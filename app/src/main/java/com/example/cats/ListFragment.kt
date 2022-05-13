package com.example.cats

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cats.databinding.FragmentListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class ListFragment : Fragment() {
    private lateinit var binding: FragmentListBinding
    private val listViewModel: FactsListViewModel by viewModels()
    private lateinit var rvAdapter: FactsListAdapter
    private var progressBarState : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //listViewModel = ViewModelProvider(this).get(FactsListViewModel::class.java)
        rvAdapter = FactsListAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
        listViewModel.getFacts()
        collectData()

        binding.refreshBtn.setOnClickListener {
            listViewModel.getFacts()
        }

    }

    private fun setRecyclerView() {
        binding.itemListRv.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            adapter = rvAdapter
        }
    }

    private fun collectData(){
        lifecycleScope.launch{
            listViewModel.facts.collect{
                event ->
                when(event){
                    is FactsListViewModel.CatFactsEvent.Success -> {
                        rvAdapter.setFactsList(event.result as ArrayList<CatFactModelItem>)
                        binding.progressBar.visibility = View.GONE
                    }
                    is FactsListViewModel.CatFactsEvent.Failure -> {
                        binding.progressBar.visibility = View.GONE
                        binding.errorMsg.visibility = View.VISIBLE
                    }
                    is FactsListViewModel.CatFactsEvent.Loading ->{
                        binding.progressBar.visibility = View.VISIBLE
                        binding.errorMsg.visibility = View.GONE
                    }
                    else -> Unit
                }
            }
        }
    }


}