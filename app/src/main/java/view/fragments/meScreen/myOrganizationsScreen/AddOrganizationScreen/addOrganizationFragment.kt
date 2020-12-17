package view.fragments.meScreen.myOrganizationsScreen.AddOrganizationScreen

import view.fragments.registerScreen.RegisterFragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.gaiety.R

private const val ORG_NAME = "param1"
private const val ORG_ADDRESS = "param2"
private const val ORG_NUMBER = "param3"

class AddOrganizationFragment : Fragment() {
    private var orgName: String? = null
    private var orgAddress: String? = null
    private var orgNumber: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            orgName = it.getString(ORG_NAME)
            orgAddress = it.getString(ORG_ADDRESS)
            orgNumber = it.getString(ORG_NUMBER)
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
        @JvmStatic
        fun newInstance(orgName: String, orgAddress: String, orgNumber: String) =
            RegisterFragment().apply {
                arguments = Bundle().apply {
                    putString(ORG_NAME, orgName)
                    putString(ORG_ADDRESS, orgAddress)
                    putString(ORG_NUMBER, orgNumber)
                }
            }
    }
}
