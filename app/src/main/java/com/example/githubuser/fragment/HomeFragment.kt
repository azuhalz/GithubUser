package com.example.githubuser.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.R
import com.example.githubuser.adapter.UserAdapter
import com.example.githubuser.data.Resource
import com.example.githubuser.data.remote.response.UserResponse
import com.example.githubuser.databinding.FragmentHomeBinding
import com.example.githubuser.helper.ViewModelFactory
import com.example.githubuser.view.HomeViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireActivity())
        val homeViewModel: HomeViewModel by viewModels {
            factory
        }

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvGithub.layoutManager = layoutManager

        binding.searchView.apply {
            queryHint = context.getString(R.string.search_user)
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    homeViewModel.searchUser(query).observe(viewLifecycleOwner) {
                        when (it) {
                            is Resource.Loading -> showLoading(true)
                            is Resource.Error -> {
                                showLoading(false)
                            }
                            is Resource.Success -> {
                                showLoading(false)
                                setListData(it.data)
                            }
                        }
                    }
                    clearFocus()
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText == null || newText.isEmpty()) {
                        binding.rvGithub.visibility = View.INVISIBLE
                    }
                    return true
                }
            })
        }
    }

    private fun setListData(listGithub: List<UserResponse>) {
        val adapter = listGithub.let { UserAdapter(it) }
        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: UserResponse) {
                val toDetailFragment = HomeFragmentDirections.actionHomeFragmentToDetailUser()
                toDetailFragment.username = data.login
                findNavController().navigate(toDetailFragment)
            }
        })
        binding.rvGithub.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean?) {
        binding.apply {
            progressBar.visibility = if (isLoading == true) View.VISIBLE else View.INVISIBLE
            rvGithub.visibility = if (isLoading == true) View.INVISIBLE else View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}