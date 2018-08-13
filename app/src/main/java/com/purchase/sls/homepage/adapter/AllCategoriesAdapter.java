package com.purchase.sls.homepage.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.purchase.sls.R;
import com.purchase.sls.address.adapter.AddressListAdapter;
import com.purchase.sls.common.widget.GridSpacesItemDecoration;
import com.purchase.sls.data.entity.AddressInfo;
import com.purchase.sls.data.entity.AllCategoriesInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AllCategoriesAdapter extends RecyclerView.Adapter<AllCategoriesAdapter.AllCategoriesView> {
    private LayoutInflater layoutInflater;
    private List<AllCategoriesInfo> allCategoriesInfos;
    private Context context;

    public AllCategoriesAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<AllCategoriesInfo> allCategoriesInfos) {
        this.allCategoriesInfos = allCategoriesInfos;
        notifyDataSetChanged();
    }

    @Override
    public AllCategoriesView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.adapter_all_categories, parent, false);
        return new AllCategoriesView(view);
    }

    @Override
    public void onBindViewHolder(AllCategoriesView holder, int position) {
        AllCategoriesInfo allCategoriesInfo = allCategoriesInfos.get(holder.getAdapterPosition());
        holder.bindData(allCategoriesInfo);
    }

    @Override
    public int getItemCount() {
        return allCategoriesInfos == null ? 0 : allCategoriesInfos.size();
    }

    public class AllCategoriesView extends RecyclerView.ViewHolder implements CategoriesItemAdapter.OnCgItemClickListener{
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.categories_item_rv)
        RecyclerView categoriesItemRv;

        private CategoriesItemAdapter categoriesItemAdapter;
        public AllCategoriesView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            categoriesItemAdapter=new CategoriesItemAdapter();
            categoriesItemAdapter.setOnCgItemClickListener(this);
            categoriesItemRv.setLayoutManager(new GridLayoutManager(context, 3));
            int space = 1;
            categoriesItemRv.addItemDecoration(new GridSpacesItemDecoration(space, false));
            categoriesItemRv.setAdapter(categoriesItemAdapter);
        }

        public void bindData(AllCategoriesInfo allCategoriesInfo) {
            name.setText(allCategoriesInfo.getName());
            categoriesItemAdapter.setData(allCategoriesInfo.getCategoriesItemInfos());
        }

        @Override
        public void returnItem(String id, String name) {
            if(onAllCgClickListener!=null){
                onAllCgClickListener.returnId(id,name);
            }
        }
    }

    public interface OnAllCgClickListener {
        void returnId(String id, String name);
    }

    private OnAllCgClickListener onAllCgClickListener;

    public void setOnAllCgClickListener(OnAllCgClickListener onAllCgClickListener) {
        this.onAllCgClickListener = onAllCgClickListener;
    }
}
