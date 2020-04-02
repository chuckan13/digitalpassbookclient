//package com.example.digitalpassbook2.ui
//
//import android.os.Bundle
//import androidx.fragment.app.Fragment
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Button
//import androidx.navigation.fragment.findNavController
//
//import com.example.digitalpassbook2.R
//import kotlinx.android.synthetic.main.fragment_login(obselete).*
//
///**
// * A simple [Fragment] subclass.
// */
//class LoginFragment : Fragment() {
//
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?): View? {
//
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_login(obselete), container, false)
//    }
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        view.findViewById<Button>(R.id.activity_login_btn).setOnClickListener {
//            findNavController().navigate(R.id.action_LoginFragment_to_HomeFragment)
//        }
//    }
//
//}
