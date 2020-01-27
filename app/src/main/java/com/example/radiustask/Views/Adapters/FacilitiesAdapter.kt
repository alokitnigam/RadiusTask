package com.example.divumtask.Adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.radiustask.DI.Models.Facility
import com.example.radiustask.DI.Models.Option
import com.example.radiustask.R
import com.example.radiustask.Views.Adapters.SpinnerAdapter
import io.realm.RealmList


class FacilitiesAdapter: RecyclerView.Adapter<FacilitiesAdapter.ViewHolder>() {

    private var list: ArrayList<Facility> = ArrayList()
    private lateinit var onItemSelectedListener: OnSpinnerItemSelectedListener
    var lisofoptions:RealmList<Option> = RealmList()
    var currentExcludedOptions:RealmList<String> = RealmList<String>()
    val currentSelectedOptions:HashMap<String,Option> = HashMap<String,Option>()

    fun setOnItemSelectedListener(onSpinnerItemSelectedListener: OnSpinnerItemSelectedListener){
        this.onItemSelectedListener = onSpinnerItemSelectedListener
    }
    inner class ViewHolder( itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(get: Facility) {
            facilityName.text = get.name
            val options = get.options
            options!!.add(0, Option(id = "default",icon="default",name = "Select"))
            val spinnerAdapter = SpinnerAdapter(itemView.context,R.layout.customspinner,options)
            optionsSpinner.adapter = spinnerAdapter


            optionsSpinner.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    if(position != 0){
                       // options[position]?.let { onItemSelectedListener.onItemSelected(it) }
                        if(currentExcludedOptions.contains(options[position]!!.id)){
                            optionsSpinner.setSelection(0)
                            Toast.makeText(itemView.context,"This Option cannot be selected",Toast.LENGTH_SHORT).show()
                        }else{

                            if(currentSelectedOptions.containsKey(get.facility_id)){
                                for (i in 0 until currentSelectedOptions[get.facility_id]!!.excludedOptionsIds!!.size){

                                    currentExcludedOptions.remove(currentSelectedOptions[get.facility_id]!!.excludedOptionsIds!![i])
                                }
                            }
                            currentSelectedOptions.put(get.facility_id!!,options[position]!!)


                            for (id in options[position]!!.excludedOptionsIds!!){
                                if(!id.isNullOrEmpty())
                                    currentExcludedOptions.add(id)
                            }

                        }

                        Log.i("","")

                    }

                }

            }


        }



        private val facilityName: TextView = itemView.findViewById(R.id.facilityname)
        private val optionsSpinner: Spinner = itemView.findViewById(R.id.optionsspinner)

    }



    public fun setList(list: List<Facility>)
    {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }
    fun clear() {
        list.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.facility_view_item, parent, false)
        return ViewHolder(view)    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list.get(position));
    }

    fun setOptions(options: RealmList<Option>) {
        lisofoptions = options

    }

    interface OnSpinnerItemSelectedListener{
        fun onItemSelected( option: Option)
    }
}