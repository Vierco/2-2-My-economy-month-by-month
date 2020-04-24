package com.sergio.alvarez.mieconomia

import android.view.View
import com.sergio.alvarez.mieconomia.App.Companion.res
import me.rishabhkhanna.customtogglebutton.CustomToggleButton

class MonthsHandler {

    companion object {

        val listOfMonths: MutableList<String> = mutableListOf()

        fun months(v: View) {
            if (v is CustomToggleButton) {
                val checked: Boolean = v.isChecked

                when (v.id) {
                    R.id.ch_jan -> {
                        if (checked) {
                            listOfMonths.add(res.getString(R.string.m_jan))
                        } else {
                            listOfMonths.remove(res.getString(R.string.m_jan))
                        }
                    }
                    R.id.ch_feb -> {
                        if (checked) {
                            listOfMonths.add(res.getString(R.string.m_feb))
                        } else {
                            listOfMonths.remove(res.getString(R.string.m_feb))
                        }
                    }
                    R.id.ch_mar -> {
                        if (checked) {
                            listOfMonths.add(res.getString(R.string.m_mar))
                        } else {
                            listOfMonths.remove(res.getString(R.string.m_mar))
                        }
                    }
                    R.id.ch_apr -> {
                        if (checked) {
                            listOfMonths.add(res.getString(R.string.m_apr))
                        } else {
                            listOfMonths.remove(res.getString(R.string.m_apr))
                        }
                    }
                    R.id.ch_may -> {
                        if (checked) {
                            listOfMonths.add(res.getString(R.string.m_may))
                        } else {
                            listOfMonths.remove(res.getString(R.string.m_may))
                        }
                    }
                    R.id.ch_jun -> {
                        if (checked) {
                            listOfMonths.add(res.getString(R.string.m_jun))
                        } else {
                            listOfMonths.remove(res.getString(R.string.m_jun))
                        }
                    }
                    R.id.ch_jul -> {
                        if (checked) {
                            listOfMonths.add(res.getString(R.string.m_jul))
                        } else {
                            listOfMonths.remove(res.getString(R.string.m_jul))
                        }
                    }
                    R.id.ch_aug -> {
                        if (checked) {
                            listOfMonths.add(res.getString(R.string.m_aug))
                        } else {
                            listOfMonths.remove(res.getString(R.string.m_aug))
                        }
                    }
                    R.id.ch_sep -> {
                        if (checked) {
                            listOfMonths.add(res.getString(R.string.m_sep))
                        } else {
                            listOfMonths.remove(res.getString(R.string.m_sep))
                        }
                    }
                    R.id.ch_oct -> {
                        if (checked) {
                            listOfMonths.add(res.getString(R.string.m_oct))
                        } else {
                            listOfMonths.remove(res.getString(R.string.m_oct))
                        }
                    }
                    R.id.ch_nov -> {
                        if (checked) {
                            listOfMonths.add(res.getString(R.string.m_nov))
                        } else {
                            listOfMonths.remove(res.getString(R.string.m_nov))
                        }
                    }
                    R.id.ch_dec -> {
                        if (checked) {
                            listOfMonths.add(res.getString(R.string.m_dec))
                        } else {
                            listOfMonths.remove(res.getString(R.string.m_dec))
                        }
                    }
                }
            }
        }

        fun addAllMonths() {
            inf("fun addAllMonths")
            listOfMonths.add(res.getString(R.string.m_jan))
            listOfMonths.add(res.getString(R.string.m_feb))
            listOfMonths.add(res.getString(R.string.m_mar))
            listOfMonths.add(res.getString(R.string.m_apr))
            listOfMonths.add(res.getString(R.string.m_may))
            listOfMonths.add(res.getString(R.string.m_jun))
            listOfMonths.add(res.getString(R.string.m_jul))
            listOfMonths.add(res.getString(R.string.m_aug))
            listOfMonths.add(res.getString(R.string.m_sep))
            listOfMonths.add(res.getString(R.string.m_oct))
            listOfMonths.add(res.getString(R.string.m_nov))
            listOfMonths.add(res.getString(R.string.m_dec))
        }

    }
}