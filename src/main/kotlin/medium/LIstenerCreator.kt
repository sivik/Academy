package medium

interface OnItemClickListener {
    fun onClick(download: Boolean)
}

var progressListener: OnItemClickListener? = null

var onItemClickInterface : OnItemClickListener = object : OnItemClickListener {
    override fun onClick(download: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}