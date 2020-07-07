package com.axxess.android.axxesschallenge.app.activity;

import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;
import com.afollestad.materialdialogs.MaterialDialog;
import com.axxess.android.axxesschallenge.R;
import com.axxess.android.axxesschallenge.app.adapter.CommentListAdapter;
import com.axxess.android.axxesschallenge.app.model.search.ImgurDataItem;
import com.axxess.android.axxesschallenge.app.room.entities.CommentsDetails;
import com.axxess.android.axxesschallenge.app.room.entities.ImageDetails;
import com.axxess.android.axxesschallenge.app.room.entities.ImageDetailsWithComments;
import com.axxess.android.axxesschallenge.app.utils.MConstants;
import com.axxess.android.axxesschallenge.app.viewModel.DetailsActivityViewModel;
import com.axxess.android.axxesschallenge.core.ui.ViewState;
import com.axxess.android.axxesschallenge.core.ui.base.DaggerActivity;
import com.axxess.android.axxesschallenge.databinding.ActivityDetailsBinding;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import javax.inject.Inject;

public class DetailsActivity extends DaggerActivity implements View.OnClickListener {

    private static final String TAG = "DetailsActivity";

    private ActivityDetailsBinding binding;
    @Inject
    public DetailsActivityViewModel viewModel;
    private ImgurDataItem itemDetailsIntent;
    private CommentListAdapter commentListAdapter = new CommentListAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getIntentData();
        setUpToolBar();


        setListenersToView();
        setDataToView();

        setObservers();
    }

    private void setObservers() {
        viewModel.getImageDetails(itemDetailsIntent.getId());
        viewModel.get_imageDetails().observe(this, state -> {
            if (state instanceof ViewState.Success) {
                commentListAdapter.setData((((ViewState.Success<ImageDetailsWithComments>) state).component1()).getCommentsList());
            }
        });
    }

    private void setListenersToView() {
        binding.commentRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.commentRecyclerView.setAdapter(commentListAdapter);

        binding.addComment.setOnClickListener(this);
    }

    private void getIntentData() {
        itemDetailsIntent = getIntent().getParcelableExtra(MConstants.SEARCH_ITEM_DETAILS_INTENT_FILTER_NAME);
    }

    private void setDataToView() {
        Glide.with(this)
                .load(MConstants.IMAGE_BASE_URL.concat(itemDetailsIntent.getCover()).concat(MConstants.IMAGE_EXTENSION))
                .placeholder(getProgressDrawable())
                .error(R.drawable.ic_baseline_error_outline)
                .into(binding.detailItemIv);
    }

    private void setUpToolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(itemDetailsIntent.getTitle());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (itemDetailsIntent.getTitle() != null)
            getSupportActionBar().setTitle(itemDetailsIntent.getTitle());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private CircularProgressDrawable getProgressDrawable() {
        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(this);
        circularProgressDrawable.setStrokeWidth(getResources().getDimension(R.dimen.circular_progress_drawable_stroke_width));
        circularProgressDrawable.setCenterRadius(getResources().getDimension(R.dimen.circular_progress_drawable_stroke_radius));
        circularProgressDrawable.setColorSchemeColors(ContextCompat.getColor(this, R.color.thumbnail_progress_color));
        circularProgressDrawable.start();
        return circularProgressDrawable;
    }

    @Override
    public void onClick(View view) {

        if (view == binding.addComment) {
            showAddCommentDialog();
        }
    }

    private void showAddCommentDialog() {
        new MaterialDialog.Builder(this)
                .title(R.string.add_comment_dialog_title)
                .positiveText(R.string.add_comment_dialog_positive_btn_text)
                .negativeText(R.string.add_comment_dialog_negative_btn_text)
                .inputType(InputType.TYPE_CLASS_TEXT)
                .input(R.string.details_activity_search_et_hint, R.string.details_activity_search_et_prefill, (dialog, comment) -> {
                    if (comment.length() > 0) {
                        saveCommentInLocalDB(comment.toString());
                    }
                }).show();
    }

    private void saveCommentInLocalDB(String comment) {

        binding.commentRecyclerView.scrollToPosition(0);

        ImageDetails imageDetails = new ImageDetails(itemDetailsIntent.getId(), itemDetailsIntent.getTitle());

        ArrayList<CommentsDetails> commentsDetailsList = new ArrayList<>();

        CommentsDetails commentsDetails = new CommentsDetails(System.currentTimeMillis(), comment, itemDetailsIntent.getId());
        commentsDetailsList.add(commentsDetails);

        imageDetails.setCommentsList(commentsDetailsList);

        viewModel.updateImageDetails(imageDetails);
    }
}