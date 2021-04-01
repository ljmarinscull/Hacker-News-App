package com.project.hackernews.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.hackernews.NewsApplication
import com.project.hackernews.R
import com.project.hackernews.data.model.NewObject
import com.project.hackernews.databinding.MainFragmentBinding
import com.project.hackernews.ui.main.adapters.NewsAdapter
import com.project.hackernews.ui.main.adapters.SwipeToDeleteCallback
import com.project.hackernews.utils.Utils
import com.project.hackernews.utils.Result

class MainFragment : Fragment(), NewsAdapter.OnItemClickListener {

    private val viewModel: MainViewModel by activityViewModels()

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var mAdapter: NewsAdapter

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.swipeRefreshLayout.setOnRefreshListener {
            val ids = NewsApplication.getNewsDeletedId(requireContext())
            viewModel.setDeletedIds(ids.toList())
            viewModel.getNews(Utils.isOnline(requireContext()))
        }

        val itemDecorator = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        itemDecorator.setDrawable(ResourcesCompat.getDrawable(resources, R.drawable.vertical_divider, null)!!)
        binding.recyclerView.addItemDecoration(
                itemDecorator
        )

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        mAdapter = NewsAdapter(requireContext(),this, arrayListOf())
        binding.recyclerView.adapter = mAdapter
        val itemTouchHelper = ItemTouchHelper(SwipeToDeleteCallback(mAdapter))
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)

        viewModel.newsLiveData.observe(viewLifecycleOwner, Observer {
            it ?: return@Observer

            if (it is Result.Success){
                updatedNews(it.data)
                binding.swipeRefreshLayout.isRefreshing = false
            }
        })
    }

    private fun updatedNews(data: ArrayList<NewObject>) {
        mAdapter.setItems(data)
        mAdapter.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(obj: NewObject) {
        val action = MainFragmentDirections.actionMainFragmentToDetailsFragment(obj.storyUrl)
        findNavController().navigate(action)
    }
}