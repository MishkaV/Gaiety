package view.fragments.meScreen.aboutMe

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import com.example.gaiety.R
import view.activities.firebaseRequests


class AboutMe : Fragment() {
    lateinit var itemCheck: MenuItem
    private var showLayout : Boolean = false
    lateinit var frontLayout : RelativeLayout
    lateinit var backLayout : RelativeLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about_me, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("ABOUT ME", "Success")
        firebaseRequests.getAboutMe(view)
    }
}
