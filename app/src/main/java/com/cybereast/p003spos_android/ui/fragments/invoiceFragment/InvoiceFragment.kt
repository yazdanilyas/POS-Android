package com.cybereast.p003spos_android.ui.fragments.invoiceFragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.cybereast.p003spos_android.R
import com.cybereast.p003spos_android.base.BaseInterface
import com.cybereast.p003spos_android.base.BaseValidationFragment
import com.cybereast.p003spos_android.base.RecyclerViewBaseFragment
import com.cybereast.p003spos_android.constants.Constants
import com.cybereast.p003spos_android.constants.Constants.NODE_PRODUCT_QUANTITY
import com.cybereast.p003spos_android.data.adapter.RecyclerViewAdapter
import com.cybereast.p003spos_android.data.enums.TransactionType
import com.cybereast.p003spos_android.data.interfaces.ChooseProductCallBack
import com.cybereast.p003spos_android.databinding.InvoiceFragmentBinding
import com.cybereast.p003spos_android.helper.Helper
import com.cybereast.p003spos_android.models.InvoiceModel
import com.cybereast.p003spos_android.models.LedgerModel
import com.cybereast.p003spos_android.models.ProductModel
import com.cybereast.p003spos_android.ui.fragments.fragmentDialogs.choseProductDialogFragment.ChoseProductDialogFragment
import com.cybereast.p003spos_android.utils.DialogUtils
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth

class InvoiceFragment : RecyclerViewBaseFragment(),
    RecyclerViewAdapter.CallBack, BaseInterface, ChooseProductCallBack {

    companion object {
        fun newInstance() = InvoiceFragment()
    }

    private lateinit var choseProductDialog: ChoseProductDialogFragment

    private lateinit var mViewModel: InvoiceViewModel
    private lateinit var mBinding: InvoiceFragmentBinding
    private lateinit var mAdapter: RecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = InvoiceFragmentBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel = ViewModelProvider(this).get(InvoiceViewModel::class.java)
        setAdapter()
        setListeners()
        requireActivity().actionBar?.title = getString(R.string.Invoices)
    }

    override fun onPrepareAdapter(): RecyclerView.Adapter<*> {
        return if (::mAdapter.isInitialized) {
            mAdapter
        } else {
            mAdapter = RecyclerViewAdapter(this, mViewModel.mProductList)
            mAdapter
        }
    }

    override fun onViewClicked(view: View, data: Any?) {
        requireActivity().runOnUiThread {
            val productModel = data as ProductModel
            when (view.id) {
                R.id.tvDecreaseQuantity -> {
                    val selected = productModel.selectedQuantity ?: 0
                    if (selected > 1)
                        productModel.selectedQuantity = selected.minus(1)
                }
                R.id.tvIncreaseQuantityImg -> {
                    val selected = productModel.selectedQuantity ?: 0
                    val total = productModel.productQuantity ?: 0
                    if (selected < total)
                        productModel.selectedQuantity = selected.plus(1)
                }

                R.id.btnRemoveProduct -> {
                    mViewModel.mProductList.remove(productModel)
                }
            }
            mAdapter.notifyDataSetChanged()
            calculateBill()
        }
    }

    override fun onItemClick(data: Any?, position: Int) {
    }

    override fun onItemLongClick(view: View, data: Any?, position: Int) {
    }

    override fun inflateLayoutFromId(position: Int, data: Any?): Int {
        return R.layout.item_invoice
    }

    override fun onNoDataFound() {
    }

    override fun onProgress() {
        mBinding.progressBar.visibility = View.VISIBLE
    }

    override fun onResponse() {
        mBinding.progressBar.visibility = View.GONE
    }

    override fun onProductSelected(product: ProductModel) {
        product.selectedQuantity = 1
        if (product.productQuantity ?: 0 > 0) {
            if (!checkItemInInvoice(product.productId)) {
                mViewModel.mProductList.add(product)
                calculateBill()
                mAdapter.notifyDataSetChanged()
                closeChoseProductDialog()
            } else
                Toast.makeText(requireContext(), "Item Already in Cart", Toast.LENGTH_SHORT).show()
        } else
            Toast.makeText(requireContext(), "Out of Stock", Toast.LENGTH_SHORT)
                .show()
    }

    private fun setAdapter() {
        mAdapter = RecyclerViewAdapter(this, mViewModel.mProductList)
        setUpRecyclerView(
            mBinding.recyclerView,
            resources.getDimensionPixelSize(R.dimen._5dp) / 2
        )
        mBinding.recyclerView.setHasFixedSize(true)
    }

    private fun setListeners() {
        mBinding.addProductButton.setOnClickListener {
            openChoseProductDialog()
        }
        mBinding.invoiceBtn.setOnClickListener {
            if (mViewModel.mBillingAmount > 0) {
                DialogUtils.suretyDialog(
                    requireContext(),
                    resources.getString(R.string.confirmation),
                    resources.getString(R.string.are_you_sure_you_want_to_complete_this_process),
                    true,
                    object : DialogUtils.CallBack {
                        override fun onPositiveCallBack() {
                            if (Helper.isNetworkConnectionAvailable(requireContext())) {
                                onProgress()
                                writeDateToFirebase()
                            } else {
                                Toast.makeText(
                                    context,
                                    getString(R.string.noInternetTitle),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                        override fun onNegativeCallBack() {

                        }
                    }
                )
            } else {
                DialogUtils.infoAlert(
                    requireContext(),
                    resources.getString(R.string.your_cart_is_empty),
                    object : DialogUtils.AlertCallBack {
                        override fun onOkCallBack() {

                        }

                    }
                )
            }
        }
    }

    private fun checkItemInInvoice(productId: String?): Boolean {
        for (i in 0 until mViewModel.mProductList.size) {
            val product = mViewModel.mProductList[i] as ProductModel
            if (product.productId == productId)
                return true
        }
        return false
    }

    private fun writeDateToFirebase() {
        updateStockAndAddInvoice()
        writeInvoiceAndInvoiceToFirebase()
    }

    private fun updateStockAndAddInvoice() {
        for (i in 0 until mViewModel.mProductList.size) {
            val product = mViewModel.mProductList[i] as ProductModel
            updateProductToFirebase(product)
        }
    }

    private fun openChoseProductDialog() {
        choseProductDialog = ChoseProductDialogFragment.newInstance(this)
        choseProductDialog.show(
            requireActivity().supportFragmentManager,
            choseProductDialog.tag
        )
    }

    private fun closeChoseProductDialog() {
        choseProductDialog.dismiss()
    }

    private fun calculateBill() {
        var total = 0
        for (i in 0 until mViewModel.mProductList.size) {
            val model = mViewModel.mProductList[i] as ProductModel
            val items = model.selectedQuantity ?: 0
            val price = model.productSalePrice ?: 0
            total += items * price
        }
        mViewModel.mBillingAmount = total
        mBinding.tvBillValue.text = total.toString()
    }

    private fun updateProductToFirebase(product: ProductModel) {
        //updated quantity=total- selected
        val updatedQuantity = product.selectedQuantity?.let { product.productQuantity?.minus(it) }

        product.productId?.let { productId ->
            mFireStoreDbRef.collection(Constants.NODE_PRODUCTS).document(productId)
                .update(NODE_PRODUCT_QUANTITY, updatedQuantity)
                .addOnSuccessListener {
                    Log.d(TAG, "DocumentSnapshot Product Quantity successfully updated!")
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error  Product Quantity updating document", e)
                }
        }
    }

    private fun resetFields() {
        mViewModel.mBillingAmount = 0
        mBinding.tvBillValue.text = "0"
        mViewModel.mProductList.clear()
        mAdapter.notifyDataSetChanged()
    }

    private fun writeInvoiceAndInvoiceToFirebase() {
        val ledgerRef = mFireStoreDbRef.collection(Constants.NODE_LEDGER).document()
        val invoiceRef = mFireStoreDbRef.collection(Constants.NODE_INVOICE).document(ledgerRef.id)
        val date = Timestamp.now().seconds.toString().toLong()

        val ledger = LedgerModel(
            ledgerRef.id,
            date,
            TransactionType.SALE.toString(),
            mViewModel.mBillingAmount,
            0,
            FirebaseAuth.getInstance().uid
        )

        val invoice = InvoiceModel(
            ledgerRef.id,
            date,
            mViewModel.mBillingAmount,
            FirebaseAuth.getInstance().uid,
            mViewModel.mProductList as ArrayList<ProductModel>
        )

        mFireStoreDbRef.runBatch {
            ledgerRef.set(ledger)
            invoiceRef.set(invoice)
        }
            .addOnSuccessListener {
                Log.d(BaseValidationFragment.TAG, getString(R.string.doc_successfully_written))
                onResponse()
                resetFields()
            }
            .addOnFailureListener {
                Log.w(BaseValidationFragment.TAG, getString(R.string.error_writing_doc), it)
                onResponse()
            }
    }
}