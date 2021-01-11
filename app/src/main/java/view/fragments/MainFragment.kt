package view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.gaiety.R
import github.com.st235.lib_expandablebottombar.ExpandableBottomBar
import kotlinx.android.synthetic.main.fragment_main.*
import view.activities.MainActivity
import view.activities.currentFragMain
import view.fragments.homeScreen.HomeFragment
import view.fragments.mapScreen.MapFragment
import view.fragments.meScreen.MeFragment
import view.fragments.pkScreen.PkFragment

class MainFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bottomBar = view.findViewById<ExpandableBottomBar>(R.id.bottom_navigation)
        if (bottom_navigation != null) {
            val homeFrag = HomeFragment()
            val meFrag = MeFragment()
            
            val mapFragment = MapFragment()
            val pkFrag = PkFragment()
            bottomBar.onItemSelectedListener = { view, menuItem ->
                when (menuItem.itemId) {
                    R.id.ic_home -> {
                        makeCurrentFragmentMain(homeFrag, "homeFrag")
                        currentFragMain = "homeFrag"
                    }
                    R.id.ic_map -> {
                        makeCurrentFragmentMain(mapFragment, "mapFrag")
                        currentFragMain = "mapFrag"
                    }
                    R.id.ic_me -> {
                        makeCurrentFragmentMain(meFrag, "meFrag")
                        currentFragMain = "meFrag"
                    }
                    R.id.ic_PK_icon -> {
                        makeCurrentFragmentMain(pkFrag, "pkFrag")
                        currentFragMain = "pkFrag"
                    }
                }
                true
            }
        }
    }

    fun makeCurrentFragmentMain(fragment: Fragment, name: String) {
        fragmentManager?.beginTransaction()?.apply {
            replace(R.id.main_fragment, fragment)
            addToBackStack(name.toString())
            commit()
        }
    }


}
