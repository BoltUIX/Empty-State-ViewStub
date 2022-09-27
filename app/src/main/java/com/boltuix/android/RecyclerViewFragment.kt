package com.boltuix.android

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.boltuix.android.databinding.RecyclerViewFragmentBinding
import com.google.android.material.snackbar.Snackbar

class RecyclerViewFragment : Fragment() {
    private var _binding: RecyclerViewFragmentBinding? = null

    private lateinit var adapterOrder: RecyclerViewAdapter

    private val viewModel: RecyclerViewViewModel by viewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = RecyclerViewFragmentBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

            hasFixedSize()
            adapterOrder = RecyclerViewAdapter(event = { _, item ->
                   Snackbar.make(binding.recyclerView, item.label, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            })
            adapter= adapterOrder
        }

        viewModel.liveNewsData.observe(viewLifecycleOwner) { response ->
            //adapterOrder.itemDiffer.submitList(response)
            adapterOrder.submitList(response)

            binding.emptyStateLayout.run { if (response.isNullOrEmpty()) show() else hide() }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

