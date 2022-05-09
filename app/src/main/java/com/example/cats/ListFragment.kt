package com.example.cats

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cats.databinding.FragmentListBinding


class ListFragment : Fragment() {
    private lateinit var binding: FragmentListBinding
    private lateinit var listViewModel: FactsListViewModel
    private lateinit var rvAdapter: FactsListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listViewModel = ViewModelProvider(this).get(FactsListViewModel::class.java)
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
        if (listViewModel.showProgress) {
            binding.progressBar.visibility = View.VISIBLE
        }

        observerFactsLiveData()
        if (listViewModel.isResponseBodyNull()) {
            binding.errorMsg.visibility = View.VISIBLE
        }

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

    private fun observerFactsLiveData() {
        listViewModel.observeFactsLiveData().observe(viewLifecycleOwner, { facts ->
            rvAdapter.setFactsList(facts as ArrayList<CatFactModelItem>)
            if (facts.isNotEmpty()) {
                binding.errorMsg.visibility = View.GONE
                binding.progressBar.visibility = View.GONE
            }
        })
    }
}