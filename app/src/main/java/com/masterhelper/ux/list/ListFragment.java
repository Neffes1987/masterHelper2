package com.masterhelper.ux.list;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.masterhelper.R;

/**
 * A fragment representing a list of Items.
 */
public class ListFragment extends Fragment {
  View view;

  public ListFragment() {
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    view = inflater.inflate(R.layout.fragment_item_list, container, false);

    setAdapter(new ListAdapter(PlaceholderContent.ITEMS));
    return view;
  }

  public void setAdapter(ListAdapter adapter) {
    // Set the adapter
    if (view instanceof RecyclerView) {
      Context context = view.getContext();
      RecyclerView recyclerView = (RecyclerView) view;
      recyclerView.setLayoutManager(new LinearLayoutManager(context));
      recyclerView.setAdapter(adapter);
    }
  }
}