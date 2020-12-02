package com.example.gaiety.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.gaiety.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ORG_NAME = "param1"
private const val ORG_ADDRESS = "param2"
private const val ORG_NUMBER = "param3"

/**
 * A simple [Fragment] subclass.
 * Use the [RegisterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class addOrganizationFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var org_name: String? = null
    private var org_address: String? = null
    private var org_number: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            org_name = it.getString(ORG_NAME)
            org_address = it.getString(ORG_ADDRESS)
            org_number = it.getString(ORG_NUMBER)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_organization, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RegisterFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(org_name: String, org_address: String, org_number: String) =
            RegisterFragment().apply {
                arguments = Bundle().apply {
                    putString(ORG_NAME, org_name)
                    putString(ORG_ADDRESS, org_address)
                    putString(ORG_NUMBER, org_number)
                }
            }
    }
}
