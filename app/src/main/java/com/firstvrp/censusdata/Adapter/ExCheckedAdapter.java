package com.firstvrp.censusdata.Adapter;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.firstvrp.censusdata.CompanyInformation.CompanyInfomationActivity;
import com.firstvrp.censusdata.Entity.BigPlaceInfoEntity;
import com.firstvrp.censusdata.Entity.ChailPlaceInfoEntity;
import com.firstvrp.censusdata.Entity.PlaceInfoEntity;
import com.firstvrp.censusdata.Entity.SaveStyleEnum;
import com.firstvrp.censusdata.Entity.UnitsInfoEntity;
import com.firstvrp.censusdata.Http.TwitterRestClient;
import com.firstvrp.censusdata.MyApplication;
import com.firstvrp.censusdata.R;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;

import java.util.List;

/**
 * Created by chenyuan on 16/5/25.
 */
public class ExCheckedAdapter extends BaseExpandableListAdapter {
    List<UnitsInfoEntity> groupData;
    List<List<ChailPlaceInfoEntity>> childData;
    Context context;
    MyApplication application;
    static final int BIN = 2;
    static final int YI = 1;

    public ExCheckedAdapter(Context context, List<UnitsInfoEntity> groupData, List<List<ChailPlaceInfoEntity>> childData, MyApplication application) {
        this.groupData = groupData;
        this.childData = childData;
        this.context = context;
        this.application = application;
    }

    @Override
    public int getGroupCount() {
        return groupData.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childData.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupData.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childData.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        final UnitsInfoEntity unitsInfoEntity = (UnitsInfoEntity) getGroup(groupPosition);
        View view = convertView;
        if (view == null) {
            // 通过getSystemService方法实例化一个视图的填充器
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.ex_checked_view_first, null);
        }


        TextView check_type = (TextView) view.findViewById(R.id.check_type);
        TextView title = (TextView) view.findViewById(R.id.content_001);
        ImageView look = (ImageView) view.findViewById(R.id.tubiao);
        ImageView image = (ImageView) view.findViewById(R.id.ImageView01);

        title.setText(unitsInfoEntity.getUnits_name());

        if (unitsInfoEntity.getAudit_state() == 0){
            check_type.setVisibility(View.GONE);
        }
        if (unitsInfoEntity.getAudit_state() == 1) {
            if (unitsInfoEntity.getAudit_options() != null)
                check_type.setText(unitsInfoEntity.getAudit_options());
            else
                check_type.setVisibility(View.GONE);
        }
        if (unitsInfoEntity.getAudit_state() == 2) {
            if (unitsInfoEntity.getAudit_options() != null)
                check_type.setText(unitsInfoEntity.getAudit_options());
            else
                check_type.setVisibility(View.GONE);
        }

        //判断实例可以展开，如果可以则改变右侧的图标
        if (isExpanded)
            image.setBackgroundResource(R.drawable.ico_pull_down);
        else image.setBackgroundResource(R.drawable.ico_pull_up);

        look.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (unitsInfoEntity.getAudit_state() == 2)
                    application.setSavestyle(SaveStyleEnum.UNCHECK);
                if (unitsInfoEntity.getAudit_state() == 1)
                    application.setSavestyle(SaveStyleEnum.CHECKED);
                if (unitsInfoEntity.getAudit_state() == 0)
                    application.setSavestyle(SaveStyleEnum.CHECKPENDING);

                application.setUnitsInfoEntity(unitsInfoEntity);
                application.setUnitsID(unitsInfoEntity.getId());

                Intent intent = new Intent(context, CompanyInfomationActivity.class);
                intent.putExtra("organizationCode", unitsInfoEntity.getOrg_code());
                intent.putExtra("type", "companyinfo");
                context.startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final ChailPlaceInfoEntity chailPlaceInfoEntity = (ChailPlaceInfoEntity) getChild(groupPosition, childPosition);
        View view = convertView;
        if (view == null) {
            //填充视图
            LayoutInflater inflater = (LayoutInflater) context.getSystemService
                    (Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.ex_checked_view_second, null);
        }
        final TextView title = (TextView) view.findViewById
                (R.id.child_text);

//        ImageView child_image = (ImageView) view.findViewById(R.id.child_image);

        TextView ico = (TextView) view.findViewById(R.id.ico);
        TextView child_type = (TextView) view.findViewById(R.id.child_type);
        if (chailPlaceInfoEntity.getAudit_state() == 0)
            child_type.setVisibility(View.GONE);
        if (chailPlaceInfoEntity.getAudit_state() == 1) {
            child_type.setText(chailPlaceInfoEntity.getAudit_options());
        }
        if (chailPlaceInfoEntity.getAudit_state() == 2) {
            child_type.setText(chailPlaceInfoEntity.getAudit_options());
        }

        if (chailPlaceInfoEntity.getType() == YI) {
            ico.setText("乙：");
        } else {
            ico.setText("丙：");
        }

        title.setText(chailPlaceInfoEntity.getPlaceName());


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (chailPlaceInfoEntity.getAudit_state() == 2)
                    application.setSavestyle(SaveStyleEnum.UNCHECK);
                if (chailPlaceInfoEntity.getAudit_state() == 1)
                    application.setSavestyle(SaveStyleEnum.CHECKED);
                if (chailPlaceInfoEntity.getAudit_state() == 0)
                    application.setSavestyle(SaveStyleEnum.CHECKPENDING);

                if (chailPlaceInfoEntity.getType() == YI) {
                    String url = String.format(context.getString(R.string.url_get_placeinfo_special), chailPlaceInfoEntity.getPlaceId());
                    TwitterRestClient.get(url, null, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            Gson gson = new Gson();
                            PlaceInfoEntity placeInfoEntities = gson.fromJson(new String(responseBody), PlaceInfoEntity.class);
                            if (statusCode == 200 && placeInfoEntities != null) {

                                Intent intent = new Intent(context, CompanyInfomationActivity.class);

                                intent.putExtra("type", placeInfoEntities.getPlace_code());

                                application.setUnitsInfoEntity((UnitsInfoEntity) getGroup(groupPosition));

                                application.setUnitsID(placeInfoEntities.getUnits_id());

                                application.setTypeID(Integer.parseInt(placeInfoEntities.getPlace_code()));

                                application.setPlaceInfoEntity(placeInfoEntities);

                                context.startActivity(intent);
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                            Log.v("getChildDataonFailure", new String(responseBody));
                        }
                    });
                } else {
                    String url = String.format(context.getString(R.string.url_get_bigplaceinfo_special), chailPlaceInfoEntity.getPlaceId());
                    TwitterRestClient.get(url, null, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            Gson gson = new Gson();
                            BigPlaceInfoEntity bigPlaceInfoEntity = gson.fromJson(new String(responseBody), BigPlaceInfoEntity.class);
                            if (statusCode == 200 && bigPlaceInfoEntity != null) {
                                Intent intent = new Intent(context, CompanyInfomationActivity.class);
                                intent.putExtra("type", "big");


                                application.setUnitsInfoEntity((UnitsInfoEntity) getGroup(groupPosition));

                                application.setUnitsID(bigPlaceInfoEntity.getUnits_id());

                                application.setTypeID(Integer.parseInt(bigPlaceInfoEntity.getPlace_code()));

                                application.setBigPlaceInfoEntity(bigPlaceInfoEntity);

                                context.startActivity(intent);
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                            Log.v("getChildDataonFailure", new String(responseBody));
                        }
                    });
                }

            }
        });
        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
