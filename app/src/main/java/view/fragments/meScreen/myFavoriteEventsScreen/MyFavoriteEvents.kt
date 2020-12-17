package view.fragments.meScreen.myFavoriteEventsScreen

import model.clientData.Client
import model.clientData.orders.Orders
import model.clientData.organizations.Organizations
import model.NetworkRequests
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gaiety.NumAdapterMyFavoriteEvent
import com.example.gaiety.R

class MyFavoriteEvents : Fragment() {
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
        var orientation = RecyclerView.HORIZONTAL
        var spanCount = 2
        numList = view.findViewById(R.id.recyclerViewMyTickets)
        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            orientation = RecyclerView.VERTICAL
            spanCount = 1
        }

        val layoutManager = GridLayoutManager(requireContext(), spanCount, orientation, false)
        val numAdapter = createNumAdapter()
        numList.layoutManager = layoutManager
        numList.adapter = numAdapter

        numList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) //check for scroll down
                {
                    var visibleItemCount = layoutManager.getChildCount()
                    var totalItemCount = layoutManager.getItemCount()
                    var pastVisiblesItems = layoutManager.findFirstVisibleItemPosition()
                    if (visibleItemCount + pastVisiblesItems >= totalItemCount) {
                        Log.v("TAG", "Last Item Wow !")
                        NetworkRequests().myTicketsRequest(numAdapter)
                    }
                }
            }
        })

        NetworkRequests().myTicketsRequest(numAdapter)
    }

    fun createNumAdapter(): NumAdapterMyFavoriteEvent {
        val client = Client(true, "","","", listOf<Organizations>(), listOf<Orders>())
        val numAdapter = NumAdapterMyFavoriteEvent(client)
        return numAdapter
    }
}