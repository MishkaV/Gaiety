package view.fragments.homeScreen
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.CompoundButton
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gaiety.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.maxkeppeler.bottomsheets.calendar.CalendarMode
import com.maxkeppeler.bottomsheets.calendar.CalendarSheet
import com.maxkeppeler.bottomsheets.calendar.SelectionMode
import com.maxkeppeler.bottomsheets.input.InputSheet
import com.maxkeppeler.bottomsheets.input.type.InputEditText
import model.EventData.Event
import model.EventData.Value
import model.NetworkRequests
import presenter.homeScreen.NumAdapter
import util.EndlessRecyclerViewScrollListener
import java.util.*
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import java.util.regex.Matcher
import java.util.regex.Pattern


class HomeFragment : Fragment() {
    lateinit var numList: RecyclerView
    private lateinit var numAdapter: NumAdapter
    private lateinit var fab : FloatingActionButton
    private lateinit var layoutManager : GridLayoutManager
    private lateinit var progressBar : ProgressBar
    private var cities : String? = null
    private var keywords : String? = null
    private var price_min : String? = null
    private var price_max : String? = null
    private var starts_at_min : String? = null
    private var starts_at_max : String? = null
    private var list : MutableList<String> = mutableListOf()
    private lateinit var chipGroup: ChipGroup
    private val listOfCities = listOf("Москва", "Санкт-Петербург", "Новосибирск", "Екатеринбург", "Казань", "Нижний Новгород",
    "Челябинск", "Самара", "Омск", "Ростов-на-Дону", "Уфа", "Красноярск", "Воронеж", "Пермь", "Волгоград")

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
        else {
            orientation = RecyclerView.VERTICAL
            spanCount = 2
        }

        layoutManager = GridLayoutManager(requireContext(), spanCount, orientation, false)
        numAdapter = createNumAdapter()
        numList.layoutManager = layoutManager
        numList.adapter = numAdapter

        fab = view.findViewById<FloatingActionButton>(R.id.floating_action_button)
        fab.setOnClickListener() {
            callFilterBottomSheet()
        }

        chipGroup = view.findViewById(R.id.cityChips)
        val layoutInflater : LayoutInflater = LayoutInflater.from(context)
        for (i in listOfCities) {
            val chip : Chip = layoutInflater.inflate(R.layout.layout_chip_entry, chipGroup, false) as Chip
            chip.text = i
            chipGroup.addView(chip)
            chip.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
                handleChipCheckChanged(
                    (buttonView as Chip),
                    isChecked
                )
            })
        }

        val scrollListener = object : EndlessRecyclerViewScrollListener(layoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                loadNextDataFromApi(page)
            }
        }
        progressBar = view.findViewById(R.id.progressBar)
        numList.addOnScrollListener(scrollListener)
        NetworkRequests().eventRequest(numList, 0, numAdapter)
    }

    private fun createNumAdapter(): NumAdapter {
        val event = Event(0, listOf<Value>())
        return NumAdapter(event)
    }

    fun loadNextDataFromApi(offset: Int) {
        // Send an API request to retrieve appropriate paginated data
        //  --> Send the request including an offset value (i.e `page`) as a query parameter.
        //  --> Deserialize and construct new model objects from the API response
        //  --> Append the new data objects to the existing set of items inside the array of items
        //  --> Notify the adapter of the new items made with `notifyItemRangeInserted()`
        val totalItemCount = layoutManager.itemCount
        showProgressView()
        NetworkRequests().eventRequestDataFiltered(
            numList,
            view,
            totalItemCount,
            numAdapter,
            cities,
            keywords,
            price_min,
            price_max,
            starts_at_min,
            starts_at_max
        )
    }

    fun showProgressView() {
        progressBar.visibility = View.VISIBLE
    }

    private fun callFilterBottomSheet() {
        val sheet = context?.let {
            InputSheet().build(it) {
                onNegative(R.string.refuse_sheet_button)
                title(R.string.str_filter)
                content(R.string.str_filter_help)

                with(InputEditText {
                    label(R.string.str_cities)
                    hint(R.string.str_cities)
                    cities?.let { defaultValue(it) }
                    resultListener { value ->
                        if (value != null && value == "") cities = null else if (value != null) cities = value.toString()
                    } // Input value changed when form finished
                })

                with(InputEditText {
                    label(R.string.str_keywords)
                    hint(R.string.str_keywords)
                    keywords?.let { defaultValue(it) }
                    resultListener { value ->
                        if (value != null && value == "") keywords = null else if (value != null) keywords = value.toString()
                    } // Input value changed when form finished
                })

                with(InputEditText {
                    label(R.string.str_price_min)
                    hint(R.string.str_price_min)
                    price_min?.let { defaultValue(it) }
                    resultListener { value ->
                        if (value != null && value != "") {
                            try {
                                value.toString().toInt()
                                price_min = value.toString()
                            } catch (e: Exception) {
                                Toast.makeText(
                                    context,
                                    R.string.price_error_message,
                                    Toast.LENGTH_LONG
                                ).show()
                                callFilterBottomSheet()
                            }
                        } else if (value == "") {
                            price_min = null
                        }
                    }
                })

                with(InputEditText {
                    label(R.string.str_price_max)
                    hint(R.string.str_price_max)
                    price_max?.let { defaultValue(it) }
                    resultListener { value ->
                        if (value != null && value != "") {
                            try {
                                value.toString().toInt()
                                price_max = value.toString()
                            } catch (e: Exception) {
                                Toast.makeText(
                                    context,
                                    R.string.price_error_message,
                                    Toast.LENGTH_LONG
                                ).show()
                                callFilterBottomSheet()
                            }
                        } else if (value == "") {
                            price_max = null
                        }
                    }
                })

                with(InputEditText {
                    label(R.string.str_starts_at_min)
                    hint(R.string.str_starts_at_min)
                    if (starts_at_min != null && starts_at_max != null) {
                        defaultValue("$starts_at_min - $starts_at_max")
                    }
                    changeListener {
                        val imm =
                            context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                        imm.hideSoftInputFromWindow(view?.windowToken, 0)
                        context?.let { it1 ->
                            CalendarSheet().show(it1) {
                                title("Выберите промежуток дат") // Set the title of the bottom sheet
                                selectionMode(SelectionMode.RANGE)
                                calendarMode(CalendarMode.MONTH)
                                maxRange(30)
                                onNegative(R.string.refuse_sheet_button)
                                onPositive { dateStart, dateEnd ->
                                    starts_at_min =
                                        StringBuilder().append(dateStart.get(Calendar.DAY_OF_MONTH))
                                            .append(".")
                                            .append(dateStart.get(Calendar.MONTH) + 1)
                                            .append(".")
                                            .append(dateStart.get(Calendar.YEAR))
                                            .toString()
                                    if (dateEnd != null) {
                                        starts_at_max =
                                            StringBuilder().append(dateEnd.get(Calendar.DAY_OF_MONTH))
                                                .append(".")
                                                .append(dateEnd.get(Calendar.MONTH) + 1)
                                                .append(".")
                                                .append(dateEnd.get(Calendar.YEAR)).toString()
                                    }
                                }
                            }
                        }
                        if (starts_at_min != null && starts_at_max != null) {
                            defaultValue("$starts_at_min - $starts_at_max")
                        }
                    }
                })
                onNegative { }
                onPositive {
                    numAdapter.removeAllItems()
                    numAdapter.notifyDataSetChanged()
                        NetworkRequests().eventRequestDataFiltered(
                            numList,
                            view,
                            0,
                            numAdapter,
                            cities,
                            keywords,
                            price_min,
                            price_max,
                            starts_at_min,
                            starts_at_max
                        )
                    }
                }
            }
            sheet?.show()
        }

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
        if (cities == null || cities == "") {
            val strBuilder: StringBuilder = StringBuilder()
            cities = strBuilder.append(list.joinToString()).toString()
        } else {
            val regex = Regex("[\\w- ]+")
            val listOfCurrentCities = regex.findAll(cities!!).map { it.groupValues[0] }.toList()
            if (list.isNotEmpty()) {
                val strBuilder : StringBuilder = StringBuilder(list.joinToString())
                if (listOfCurrentCities.isNotEmpty()) {
                    strBuilder.append(",")
                    for (i in listOfCurrentCities) {
                        if (i.trim() !in list && i.trim() !in listOfCities) {
                            strBuilder.append("$i,")
                        }
                    }
                    if (strBuilder.isNotEmpty()) {
                        strBuilder.deleteCharAt(strBuilder.lastIndex)
                    }
                    cities = strBuilder.toString()
                }
            } else {
                val strBuilder : StringBuilder = StringBuilder(list.joinToString())
                if (listOfCurrentCities.isNotEmpty()) {
                    for (i in listOfCurrentCities) {
                        if (i.trim() !in list && i.trim() !in listOfCities) {
                            strBuilder.append("$i,")
                        }
                    }
                    if (strBuilder.isNotEmpty()) {
                        strBuilder.deleteCharAt(strBuilder.lastIndex)
                    }
                    cities = strBuilder.toString()
                }
            }
            if (cities == "") {
                cities = null
            }
        }
        numAdapter.removeAllItems()
        numAdapter.notifyDataSetChanged()
        val totalItemCount = layoutManager.itemCount
        NetworkRequests().eventRequestDataFiltered(
            numList,
            view,
            totalItemCount,
            numAdapter,
            cities,
            keywords,
            price_min,
            price_max,
            starts_at_min,
            starts_at_max)
    }
}