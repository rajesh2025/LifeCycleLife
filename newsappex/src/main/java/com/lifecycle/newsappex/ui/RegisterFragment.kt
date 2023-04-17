package com.lifecycle.newsappex.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.lifecycle.newsappex.R
import com.lifecycle.newsappex.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {
    lateinit var registerFragmentBind: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        registerFragmentBind = FragmentRegisterBinding.inflate(layoutInflater, container, false)
        return registerFragmentBind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!::registerFragmentBind.isInitialized) {
            return
        }
//        registerFragmentBind.btnSignUp.setOnClickListener {
//            findNavController().navigate(R.id.action_registerFragment_to_mainFragment)
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        registerFragmentBind.root.removeAllViews()
    }

}