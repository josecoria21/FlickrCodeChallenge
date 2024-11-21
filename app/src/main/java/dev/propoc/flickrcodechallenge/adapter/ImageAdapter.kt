import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import dev.propoc.flickrcodechallenge.databinding.ItemDataBinding
import dev.propoc.flickrcodechallenge.model.ImageResponseModel
import dev.propoc.flickrcodechallenge.model.Item
import dev.propoc.flickrcodechallenge.view.searchpage.SearchFragmentDirections

class ImageAdapter(private val navController: NavController) : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    private var items = listOf<Item>()

    fun setData(data: List<ImageResponseModel>) {
        items = data.flatMap { it.items }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = ItemDataBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = items.size

    inner class ImageViewHolder(private val binding: ItemDataBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item) {
            binding.item = item
            binding.executePendingBindings()
            binding.root.setOnClickListener {
                val action = SearchFragmentDirections.actionSearchFragmentToDetailsFragment(item)
                navController.navigate(action)
            }
        }
    }
}

