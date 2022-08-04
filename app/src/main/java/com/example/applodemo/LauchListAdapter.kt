package com.example.applodemo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.applodemo.databinding.LaunchitemBinding
import com.example.rocketreserver.LaunchDetailsQuery

class LauchListAdapter(private val launches: List<LaunchDetailsQuery.Launch>):RecyclerView.Adapter<LauchListAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LaunchitemBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val launch = launches.get(position)

//        holder.binding.site.text = launch.site ?: ""
        holder.binding.launchItemTv.text = launch.launchFragment.site
        holder.binding.launchNameTv.text = launch.launchFragment.mission?.name

        holder.binding.imageLauchItem.load(launch.mission?.missionPatch)



    }

    override fun getItemCount(): Int {
     return launches.size

    }

    class ViewHolder(var binding: LaunchitemBinding):RecyclerView.ViewHolder(binding.root){



    }
}