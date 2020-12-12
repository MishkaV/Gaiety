package View.Fragments.MeScreen.MyTicketsScreen

import Model.NetworkRequests
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gaiety.NumAdapterMyTickets
import com.example.gaiety.R
import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException

class MyTicketsFragment : Fragment() {
    lateinit var numList: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mytickets, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        numList = view.findViewById(R.id.recyclerViewMyTickets)
        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
            numList.layoutManager =
                GridLayoutManager(requireContext(), 1, RecyclerView.VERTICAL, false)
        else
            numList.layoutManager =
                GridLayoutManager(requireContext(), 2, RecyclerView.VERTICAL, false)

        NetworkRequests().eventRequest(numList)
    }
}
