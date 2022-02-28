package com.example.newsapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.newsapp.databinding.FragmentBottomNavigationBarBinding
import com.google.android.material.navigation.NavigationBarView


class BottomNavigationBarFragment : Fragment() {
    private lateinit var binding: FragmentBottomNavigationBarBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBottomNavigationBarBinding.inflate(inflater)
        binding.bottomNavigation.setOnItemSelectedListener(activity as NavigationBarView.OnItemSelectedListener)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = BottomNavigationBarFragment()
    }
}