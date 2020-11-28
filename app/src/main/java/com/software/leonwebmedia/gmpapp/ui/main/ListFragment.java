package com.software.leonwebmedia.gmpapp.ui.main;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.software.leonwebmedia.gmpapp.MainActivity;
import com.software.leonwebmedia.gmpapp.R;
import com.software.leonwebmedia.gmpapp.adapters.CommitsListAdapter;
import com.software.leonwebmedia.gmpapp.model.Commits;
import com.software.leonwebmedia.gmpapp.utils.CommitsSelectedListener;
import com.software.leonwebmedia.gmpapp.viewmodel.MainViewModel;
import com.software.leonwebmedia.gmpapp.viewmodel.SelectedCommitViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ListFragment extends Fragment implements CommitsSelectedListener {

    @BindView(R.id.recycler_view)
    RecyclerView listView;
    @BindView(R.id.tv_error)
    TextView errorTextView;
    @BindView(R.id.loading_view) View loadingView;

    private MainViewModel mViewModel;
    private Unbinder unbinder;

    public static ListFragment newInstance() {
        return new ListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        listView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        listView.setAdapter(new CommitsListAdapter(mViewModel, this, this));
        listView.setLayoutManager(new LinearLayoutManager(getContext()));
        observeViewModel();
    }

    private void observeViewModel() {
        mViewModel.getCommits().observe(getViewLifecycleOwner(), repos -> {
            if (repos != null) {
                listView.setVisibility(View.VISIBLE);
            }
        });
        mViewModel.getError().observe(getViewLifecycleOwner(), isError -> {
            //noinspection ConstantConditions
            if (isError) {
                errorTextView.setVisibility(View.VISIBLE);
                listView.setVisibility(View.GONE);
                errorTextView.setText(R.string.api_error_repos);
            } else {
                errorTextView.setVisibility(View.GONE);
                errorTextView.setText(null);
            }
        });
        mViewModel.getLoading().observe(getViewLifecycleOwner(), isLoading -> {
            //noinspection ConstantConditions
            loadingView.setVisibility(isLoading ? View.VISIBLE : View.GONE);
            if (isLoading) {
                errorTextView.setVisibility(View.GONE);
                listView.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
            unbinder = null;
        }
    }

    @Override
    public void onCommitSelected(Commits commit) {
        SelectedCommitViewModel SelectedCommitViewModel = ViewModelProviders.of(getActivity())
                .get(SelectedCommitViewModel.class);
        SelectedCommitViewModel.setSelectedCommit(commit);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new DetailsFragment())
                .addToBackStack(null)
                .commit();

    }
}