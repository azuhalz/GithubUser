package com.example.githubuser.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.adapter.UserAdapter
import com.example.githubuser.data.remote.response.UserResponse
import com.example.githubuser.databinding.FragmentFavoriteBinding
import com.example.githubuser.helper.ViewModelFactory
import com.example.githubuser.view.FavoriteViewModel

class FavoriteFragment : Fragment() {
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireActivity())
        val favoriteViewModel: FavoriteViewModel by viewModels {
            factory
        }

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvFavorite.layoutManager = layoutManager

        favoriteViewModel.getAllChanges().observe(viewLifecycleOwner) {
            setListData(it)
        }
    }

    private fun setListData(listGithub: List<UserResponse>) {
        val adapter = UserAdapter(listGithub)
        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: UserResponse) {
                val toDetailFragment =
                    FavoriteFragmentDirections.actionFavoriteFragmentToDetailUser()
                toDetailFragment.username = data.login
                findNavController().navigate(toDetailFragment)
            }
        })
        binding.rvFavorite.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}