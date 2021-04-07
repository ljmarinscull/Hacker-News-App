package com.project.hackernews.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.hackernews.NewsApplication
import com.project.hackernews.R
import com.project.hackernews.data.model.NewObject
import com.project.hackernews.data.preferences.IPreferences
import com.project.hackernews.databinding.MainFragmentBinding
import com.project.hackernews.ui.main.adapters.NewsAdapter
import com.project.hackernews.ui.main.adapters.SwipeToDeleteCallback
import com.project.hackernews.utils.Utils
import com.project.hackernews.utils.Result
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : Fragment(), NewsAdapter.OnItemClickListener,
    SwipeToDeleteCallback.SwipeToDelete {

    private val viewModel: MainViewModel by activityViewModels()
    @Inject
    lateinit var mPreference: IPreferences
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var mAdapter: NewsAdapter
    private lateinit var idsList: List<String>

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mPreference.newsDeletedIds.asLiveData().observe(viewLifecycleOwner, Observer {
            it ?: return@Observer
            idsList = it.toList()
        })

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.setDeletedIds(idsList)
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
        val itemTouchHelper = ItemTouchHelper(SwipeToDeleteCallback(this))
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

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, position: Int) {
       lifecycleScope.launch{
           mPreference.saveNewsDeletedId((viewHolder as NewsAdapter.NewsHolder).mNews.objectID!!)
           mAdapter.removeItem(position)
       }
    }
}