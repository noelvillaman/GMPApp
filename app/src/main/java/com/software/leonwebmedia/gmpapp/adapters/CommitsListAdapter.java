package com.software.leonwebmedia.gmpapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.software.leonwebmedia.gmpapp.R;
import com.software.leonwebmedia.gmpapp.model.Commits;
import com.software.leonwebmedia.gmpapp.utils.CommitsSelectedListener;
import com.software.leonwebmedia.gmpapp.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommitsListAdapter extends RecyclerView.Adapter<CommitsListAdapter.CommitViewHolder> {

    private final List<Commits> data = new ArrayList<>();
    private final CommitsSelectedListener commitsSelectedListener;

    public CommitsListAdapter(MainViewModel viewModel, LifecycleOwner lifecycleOwner, CommitsSelectedListener repoSelectedListener) {
        this.commitsSelectedListener = repoSelectedListener;
        viewModel.getCommits().observe(lifecycleOwner, commit -> {
            data.clear();
            if (commit != null) {
                data.addAll(commit);
            }
            notifyDataSetChanged();
        });
        setHasStableIds(true);
    }

    @Override
    public CommitViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view_content, parent, false);
        return new CommitViewHolder(view, commitsSelectedListener);
    }

    @Override
    public void onBindViewHolder(CommitViewHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return 25;
    }

    @Override
    public long getItemId(int position) {
        return data.get(position).hashCode();
    }

    static final class CommitViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.commit_author) TextView commitTextTV;
        @BindView(R.id.commit_hash) TextView commitHashTV;
        @BindView(R.id.commit_message) TextView commitMessageTV;

        private Commits commits;

        CommitViewHolder(View itemView, CommitsSelectedListener commitSelectedListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(v -> {
                if (commits != null) {
                    commitSelectedListener.onCommitSelected(commits);
                }
            });
        }

        void bind(Commits commits) {
            this.commits = commits;
            commitTextTV.setText(commits.getCommit().getAuthor().getName());
            commitHashTV.setText(commits.getHash());
            commitMessageTV.setText(commits.getCommit().getMessage());
        }
    }
}
