package com.qmuiteam.qmuidemo.fragment.task;

import android.util.Log;
import android.util.Pair;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;
import com.qmuiteam.qmui.widget.tab.QMUITabBuilder;
import com.qmuiteam.qmui.widget.tab.QMUITabIndicator;
import com.qmuiteam.qmui.widget.tab.QMUITabSegment;
import com.qmuiteam.qmuidemo.R;
import com.qmuiteam.qmuidemo.base.BaseFragment;
import com.qmuiteam.qmuidemo.model.response.EnterTaskRequestResult;
import com.qmuiteam.qmuidemo.model.response.SubTask;
import com.qmuiteam.qmuidemo.model.response.Task;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.qmuiteam.qmuidemo.constants.TaskTypes.TEMPLATES_PIC;
import static com.qmuiteam.qmuidemo.constants.TaskTypes.TYPES_SINGLE;
import static com.qmuiteam.qmuidemo.constants.UrlConstants.WEBSITE_BASE;

public class DoTaskFragment extends BaseFragment {

    @BindView(R.id.topbar) QMUITopBarLayout mTopBar;
    @BindView(R.id.tabSegment) QMUITabSegment mTabSegment;
    @BindView(R.id.contentViewPager) ViewPager mContentViewPager;
    
    private int mCurrentItemCount;
    private List<Pair<SubTask, View>> mPageMap = new ArrayList<>();

    private PagerAdapter mPagerAdapter = new PagerAdapter() {
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public int getCount() {
            return mCurrentItemCount;
        }

        @Override
        public Object instantiateItem(final ViewGroup container, int position) {
            SubTask subTask = taskDetail.getSubTasks().get(position);
            View view = getPageView(subTask);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            container.addView(view, params);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            View view = (View) object;
            SubTask page = mPageMap.stream().filter(p->p.second.equals(view)).findFirst().get().first;

            int pos = taskDetail.getSubTasks().indexOf(page);
            if (pos >= mCurrentItemCount) {
                return POSITION_NONE;
            }
            return POSITION_UNCHANGED;
        }
    };

    private Task task;
    private EnterTaskRequestResult taskDetail;
    private static final String TAG = "DoTaskFragment";
    
    public void setTask(Task task) {
        this.task = task;
    }
    public void setTaskDetail(EnterTaskRequestResult taskDetail) {
        this.taskDetail = taskDetail;
        this.mCurrentItemCount = taskDetail.getSubTasks().size();
    }

    @Override
    protected View onCreateView() {
        View rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_tab_viewpager_layout, null);
        ButterKnife.bind(this, rootView);
        initTopBar();
        initTabAndPager();
        return rootView;
    }

    private void initTopBar() {
        mTopBar.addLeftBackImageButton().setOnClickListener(v -> popBackStack());
        mTopBar.setTitle(this.task.getFields().getName());
    }

    private void initTabAndPager() {
        mContentViewPager.setAdapter(mPagerAdapter);
        mContentViewPager.setCurrentItem(0, false);
        QMUITabBuilder tabBuilder = mTabSegment.tabBuilder();
        for (int i = 0; i < mCurrentItemCount; i++) {
            mTabSegment.addTab(tabBuilder.setText("Item " + (i + 1)).build());
        }
        int space = QMUIDisplayHelper.dp2px(getContext(), 16);
        mTabSegment.setIndicator(new QMUITabIndicator(
                QMUIDisplayHelper.dp2px(getContext(), 2), false, true));
        mTabSegment.setMode(QMUITabSegment.MODE_SCROLLABLE);
        mTabSegment.setItemSpaceInScrollMode(space);
        mTabSegment.setupWithViewPager(mContentViewPager, false);
        mTabSegment.setPadding(space, 0, space, 0);
    }


    private View getPageView(SubTask subTask) {
        View view = mPageMap.stream().filter(p->p.first.equals(subTask)).map(p->p.second).findFirst().orElse(null);
        if (view == null) {
            view = getSubTaskView(subTask);
            mPageMap.add(new Pair<>(subTask, view));
        }
        return view;
    }

    private View getSubTaskView(SubTask subTask){
        ScrollView parent = new ScrollView(getContext());
        LinearLayout layout = new LinearLayout(getContext());
        layout.setGravity(Gravity.CENTER_HORIZONTAL);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(100,150,100,150);
        View templateView, typeView;

        switch (task.getFields().getTemplate()){
            case TEMPLATES_PIC:{
                templateView = new ImageView(getContext());

                Log.i(TAG, "getSubTaskView: " + WEBSITE_BASE + subTask.getFile());
                Glide.with(this)
                        .load(WEBSITE_BASE + subTask.getFile())
                        .centerCrop()
                        .override(650,650)
                        .into((ImageView)templateView);
                break;
            }
            default:{
                templateView = new TextView(getContext());
                ((TextView)templateView).setGravity(Gravity.CENTER);
                ((TextView)templateView).setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                ((TextView)templateView).setText(subTask.getFile());
            }
        }
        templateView.setPadding(0,0,0,50);
        switch (task.getFields().getType()){
            case TYPES_SINGLE:{
                typeView = new QMUIGroupListView(getContext());
                int index = 1;
                for(EnterTaskRequestResult.QA qa : taskDetail.getQa_list()){
                    QMUIGroupListView.Section section = QMUIGroupListView.newSection(getContext());
                    section.setTitle("问题" + Integer.toString(index++)+": "+qa.getQuestion());
                    for(String answer : qa.getAnswers()){
                        QMUICommonListItemView item = ((QMUIGroupListView)typeView).createItemView(
                                null,
                                answer,
                                null,
                                QMUICommonListItemView.HORIZONTAL,
                                QMUICommonListItemView.ACCESSORY_TYPE_SWITCH);
                        section.addItemView(item, v->{
                            item.toggleSwitch();
                        });
                    }
                    section.addTo((QMUIGroupListView)typeView);
                }
                break;
            }
            default:{
                typeView = new TextView(getContext());
                ((TextView)typeView).setGravity(Gravity.CENTER);
                ((TextView)typeView).setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                ((TextView)typeView).setText(taskDetail.getQa_list().toString());
            }
        }

        layout.addView(templateView);
        layout.addView(typeView);
        parent.addView(layout);
        return parent;
    }
}
