package com.example.applodemo

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.apollographql.apollo3.exception.ApolloException
import com.example.applodemo.databinding.FragmentFirstBinding
import com.example.rocketreserver.LaunchDetailsQuery


class FirstFragment : Fragment() {

     private lateinit var binding : FragmentFirstBinding



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

     binding = FragmentFirstBinding.inflate(inflater)


        return binding.root



    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        lifecycleScope.launchWhenResumed {

            val response = try {

                apolloClient.query(LaunchDetailsQuery()).execute()
            } catch (e: ApolloException) {
                Log.d("LaunchList", "Failure", e)
                null
            }

            val launches = response?.data?.launches?.launches?.filterNotNull()
            if (launches != null && !response.hasErrors()) {
                val adapter = LauchListAdapter(launches)
                binding.fragRecyclerView.layoutManager = LinearLayoutManager(requireContext())
                binding.fragRecyclerView.adapter = adapter
            }
        }


    }
}
