package view.fragments.homeScreen

import model.EventData.Event
import model.EventData.Value
import model.NetworkRequests
import presenter.homeScreen.NumAdapter
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gaiety.R

import android.util.Log
import android.widget.CompoundButton
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup




class HomeFragment : Fragment() {
    lateinit var numList: RecyclerView
    private lateinit var chipGroup: ChipGroup
    private var list : MutableList<String> = mutableListOf()
    private lateinit var numAdapter: NumAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var orientation = RecyclerView.HORIZONTAL
        var spanCount = 2
        numList = view.findViewById(R.id.recyclerView)
        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            orientation = RecyclerView.VERTICAL
            spanCount = 1
        }

        val layoutManager = GridLayoutManager(requireContext(), spanCount, orientation, false)
        numAdapter = createNumAdapter()
        numList.layoutManager = layoutManager
        numList.adapter = numAdapter

        //ловим ChipGroup
        chipGroup = view.findViewById(R.id.cityChips)
        //создаем 4 Chips ставим каждый на прослушку и добавляем в chipGroup
        val layoutInflater : LayoutInflater = LayoutInflater.from(context)
        val chip1 : Chip = layoutInflater.inflate(R.layout.layout_chip_entry, chipGroup, false) as Chip
        chip1.setText("Москва")
        chipGroup.addView(chip1)
        chip1.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            handleChipCheckChanged(
                (buttonView as Chip),
                isChecked
            )
        })
        val chip2 : Chip = layoutInflater.inflate(R.layout.layout_chip_entry, chipGroup, false) as Chip
        chip2.setText("Санкт-Петербург")
        chipGroup.addView(chip2)
        chip2.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            handleChipCheckChanged(
                (buttonView as Chip),
                isChecked
            )
        })

        val chip3 : Chip = layoutInflater.inflate(R.layout.layout_chip_entry, chipGroup, false) as Chip
        chip3.setText("Новосибирск")
        chipGroup.addView(chip3)
        chip3.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            handleChipCheckChanged(
                (buttonView as Chip),
                isChecked
            )
        })

        val chip4 : Chip = layoutInflater.inflate(R.layout.layout_chip_entry, chipGroup, false) as Chip
        chip4.setText("Екатеринбург")
        chipGroup.addView(chip4)
        chip4.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            handleChipCheckChanged(
                (buttonView as Chip),
                isChecked
            )
        })




        numList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) //check for scroll down
                {
                    val visibleItemCount = layoutManager.getChildCount()
                    val totalItemCount = layoutManager.getItemCount()
                    val pastVisiblesItems = layoutManager.findFirstVisibleItemPosition()

                    if (visibleItemCount + pastVisiblesItems >= totalItemCount) {
                        if (list.isEmpty()) {
                            Log.v("TAG", "Last Item Wow !")
                            NetworkRequests().eventRequest(
                                numList,
                                totalItemCount,
                                numAdapter
                            )
                        } else {
                            //когда скроллим, если список с чипами не пустой, то вызываем метод с доступом к api с фильром по городам
                            Log.v("TAG", "Last Item Wow !")
                            NetworkRequests().eventRequestFilteredByCity(
                                numList,
                                totalItemCount,
                                numAdapter,
                                list.joinToString(",")
                            )
                        }
                    }
                }
            }
        })

        NetworkRequests().eventRequest(numList, 0, numAdapter)
    }

    fun createNumAdapter(): NumAdapter {
        val event = Event(0, listOf<Value>())
        val numAdapter = NumAdapter(event)
        return numAdapter
    }

    //обрабатываем нажатие на Chip
    //при нажатии обнуляем список, проходим по всем чипам, добавляем в список текст отмеченных чипов
    private fun handleChipCheckChanged(chip: Chip, isChecked: Boolean) {
        list = mutableListOf<String>()
        val count = chipGroup.childCount
        for (i in 0 until count) {
            val child : Chip = chipGroup.getChildAt(i) as Chip
            if (!child.isChecked) {
                continue
            } else {
                list.add(child.text.toString())
            }
        }
        numAdapter.removeAllItems()
        numAdapter.notifyDataSetChanged()
        if (list.isEmpty()) {
            NetworkRequests().eventRequest(numList, 0, numAdapter)
        } else {
            NetworkRequests().eventRequestFilteredByCity(numList, 0, numAdapter, list.joinToString(","))
        }
    }
}