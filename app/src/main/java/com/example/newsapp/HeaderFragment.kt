package com.example.newsapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.newsapp.databinding.FragmentHeaderBinding

private const val headerKey = "header_key"

class HeaderFragment : Fragment() {
    private lateinit var binding: FragmentHeaderBinding
    private lateinit var header: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            header = it.getString(headerKey) ?: ""
        }
    }

    @SuppressLint("ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHeaderBinding.inflate(inflater, container, false)
        binding.header.text = header
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(header: String) =
            HeaderFragment().apply {
                arguments = Bundle().apply {
                    putString(headerKey, header)
                }
            }
    }
}