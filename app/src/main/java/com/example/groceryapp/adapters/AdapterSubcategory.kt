package com.example.groceryapp.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.groceryapp.fragments.SubcategoryListFragment
import com.example.groceryapp.models.Subcategory

class AdapterSubcategory(fragmentManager: FragmentManager): FragmentPagerAdapter(fragmentManager){
    var mFragmentList: ArrayList<Fragment> = ArrayList()
    var mTitleList: ArrayList<String> = ArrayList()
    var subIdList: ArrayList<Int> = ArrayList()

    override fun getItem(position: Int): Fragment{
        return mFragmentList[position]
    }

    override fun getCount(): Int{
        return mFragmentList.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mTitleList[position]
    }

    fun addFragment(subcategory: Subcategory){
        mTitleList.add(subcategory.subName)
        mFragmentList.add(SubcategoryListFragment.newInstance(subcategory.subId))
    }
}