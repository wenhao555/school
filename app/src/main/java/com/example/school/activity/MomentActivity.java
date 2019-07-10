package com.example.school.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.school.R;
import com.example.school.adapter.BaseRecyclerAdapter;
import com.example.school.listeners.OnItemClickListener;
import com.example.school.model.ClassCircle;
import com.example.school.model.Comment;
import com.example.school.model.CommunityCircle;
import com.example.school.model.Discovery;
import com.example.school.model.Reply;
import com.example.school.model.User;
import com.example.school.net.Urls;
import com.example.school.utils.PrefUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.school.net.Urls.ADDCLASSCIRCLEZAN;

public class MomentActivity extends BaseActivity
{
    private ImageView add_moments;
    private BaseRecyclerAdapter adapter1, adapter2;
    private RecyclerView recyclerview;
    private List<CommunityCircle> communityCircles = new ArrayList<>();
    private List<ClassCircle> classCircles = new ArrayList<>();

    @Override
    protected int returnLayoutRes()
    {
        return R.layout.activity_moment;
    }

    @Override
    protected int returnMenuResource()
    {
        return 0;
    }

    @Override
    protected void initData(Bundle savedInstanceState)
    {

    }

    //添加赞
    private void addFav(int id)
    {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build();
        final CommunityCircle user = new CommunityCircle();
        user.setCommunityCircleId(id);
        user.setUserId(PrefUtils.getInt(context, "id", 0));

        final ClassCircle classCircle = new ClassCircle();
        classCircle.setClassCircleId(id);
        Log.e("asdasd", PrefUtils.getInt(context, "id", 0) + "");
        classCircle.setUserId(PrefUtils.getInt(context, "id", 0));
        Gson gson = new Gson();
        String Json = "";
        String urls = "";
        if (!momtype.equals("2"))
        {
            urls = ADDCLASSCIRCLEZAN;
            Json = gson.toJson(classCircle);
            Log.e("asdasd", Json);

        } else
        {

            urls = Urls.ADDCOMCIRCLEZAN;
            Json = gson.toJson(user);
        }
        final RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8"), Json);
        final Request request = new Request.Builder()
                .url(urls)
                .post(requestBody)
                .build();
        Call call = okHttpClient.newCall(request);

        call.enqueue(new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e)
            {
                Log.e("error", "connectFail");

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException
            {
                Log.e("点赞", response.body().string());

                Message msg = new Message();
                msg.what = 6;
                mHandler.sendMessage(msg);
            }
        });
    }

    //取消赞
    private void cancelFav(int id)
    {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build();
        final CommunityCircle user = new CommunityCircle();
        user.setCommunityCircleId(id);
        user.setUserId(PrefUtils.getInt(context, "id", 0));
        final ClassCircle classCircle = new ClassCircle();
        classCircle.setClassCircleId(id);
        Gson gson = new Gson();
        String Json = "";
        String urls = "";
        if (!momtype.equals("2"))
        {
            urls = Urls.DELCLASSCIRCLEZAN;
            Json = gson.toJson(classCircle);
        } else
        {
            urls = Urls.DELCOMCIRCLEZAN;
            Json = gson.toJson(user);
        }
        final RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8"), Json);
        final Request request = new Request.Builder()
                .url(urls)
                .post(requestBody)
                .build();
        Call call = okHttpClient.newCall(request);

        call.enqueue(new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e)
            {
                Log.e("error", "connectFail");

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException
            {
                Log.e("取消点赞", response.body().string());
                Message msg = new Message();
                msg.what = 7;
                mHandler.sendMessage(msg);
            }
        });
    }

    private void postReply(String string, int id, ClassCircle classCircle, CommunityCircle communityCircle)
    {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build();
        Gson gson = new Gson();
        String Json = "";

        if (momtype.equals("2"))
        {
            final CommunityCircle user = new CommunityCircle();
            user.setCommunityCircleId(id);
            Reply comment = new Reply();
            comment.setUserName(PrefUtils.getString(context, "realName", ""));
            comment.setContent(string);
            comment.setUserId(communityCircle.getUserId());
            comment.setUserName(communityCircle.getUserName());
            comment.setReplyUserId(PrefUtils.getInt(context, "id", 0));
            comment.setReplyUserName(PrefUtils.getString(context, "realName", ""));
            List<Reply> comments = new ArrayList<>();
            comments.add(comment);
            user.setReplyList(comments);
            user.setUserName(PrefUtils.getString(context, "name", ""));
            Json = gson.toJson(user);
        } else
        {
            final ClassCircle user = new ClassCircle();
            user.setClassCircleId(id);
            Reply comment = new Reply();
            comment.setUserName(PrefUtils.getString(context, "realName", ""));
            comment.setContent(string);
            comment.setUserId(communityCircle.getUserId());
            comment.setUserName(communityCircle.getUserName());
            comment.setReplyUserId(PrefUtils.getInt(context, "id", 0));
            comment.setReplyUserName(PrefUtils.getString(context, "realName", ""));
            List<Reply> comments = new ArrayList<>();
            comments.add(comment);
            user.setReplyList(comments);
//            user.setUserName(PrefUtils.getString(context, "name", ""));
            Json = gson.toJson(user);
        }
        final RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8"), Json);
        String urls = "";
        if (momtype.equals("2"))
        {
            urls = Urls.ADDCOMCIRCLEREPLY;
        } else
        {
            urls = Urls.ADDCLASSCIRCLEREPLY;
        }
        final Request request = new Request.Builder()
                .url(urls)
                .post(requestBody)
                .build();
        Call call = okHttpClient.newCall(request);

        call.enqueue(new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e)
            {
                Log.e("error", "connectFail");

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException
            {
                Log.e("回复", response.body().string());
                Message msg = new Message();
                msg.what = 2;
                mHandler.sendMessage(msg);
            }
        });
    }

    //添加评论
    private void postComment(String string, int id)
    {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build();
        String Json = "";
        Gson gson = new Gson();
        if (momtype.equals("2"))
        {
            final CommunityCircle user = new CommunityCircle();//社区圈
            user.setCommunityCircleId(id);
            user.setUserId(PrefUtils.getInt(context, "id", 0));
            user.setUserName(PrefUtils.getString(context, "name", ""));
            user.setTeacherId(PrefUtils.getInt(context, "teacherId", 0));
            Comment comment = new Comment();
            comment.setUserName(PrefUtils.getString(context, "name", ""));
            comment.setContent(string);
            comment.setUserId(PrefUtils.getInt(context, "id", 0));
            List<Comment> comments = new ArrayList<>();
            comments.add(comment);
            user.setCommentList(comments);
            user.setUserName(PrefUtils.getString(context, "realName", ""));
            Json = gson.toJson(user);
        } else
        {
            final ClassCircle user = new ClassCircle();//社区圈
            user.setClassCircleId(id);
            user.setTeacherId(PrefUtils.getInt(context, "id", 0));
//            user.setHomework(PrefUtils.getString(context, "name", "")).;
            user.setTeacherId(PrefUtils.getInt(context, "teacherId", 0));
            Comment comment = new Comment();
            comment.setUserName(PrefUtils.getString(context, "name", ""));
            comment.setContent(string);
            comment.setUserId(PrefUtils.getInt(context, "id", 0));
            List<Comment> comments = new ArrayList<>();
            comments.add(comment);
            user.setCommentList(comments);
//            user.setUserName(PrefUtils.getString(context, "realName", ""));
            Json = gson.toJson(user);
        }


        final RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8"), Json);
        String urls = "";
        if (!momtype.equals("2"))
        {
            urls = Urls.ADDCLASSCIRCLECOMMENT;
        } else
        {
            urls = Urls.ADDCOMCIRCLECOMMENT;
        }
        final Request request = new Request.Builder()
                .url(urls)
                .post(requestBody)
                .build();
        Call call = okHttpClient.newCall(request);

        call.enqueue(new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e)
            {
                Log.e("error", "connectFail");

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException
            {
                Log.e("评论", response.body().string());
                Message msg = new Message();
                msg.what = 2;
                mHandler.sendMessage(msg);
            }
        });
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if (!momtype.equals("2"))
            requestData1();
        else
            requestData2();
        if (!momtype.equals("2"))
        {
            recyclerview.setAdapter(adapter1);
        } else
        {
            recyclerview.setAdapter(adapter2);
        }
    }

    private String momtype = "";

    @Override
    protected void initView()
    {
        add_moments = findViewById(R.id.add_moments);
        momtype = getIntent().getStringExtra("momtype");
        if (momtype.equals("1") && PrefUtils.getInt(context, "userRole", 0) == 1)
        {//班级圈只有老师可以发信息
            add_moments.setVisibility(View.GONE);
        }
        add_moments.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(context, AddMomentsActivity.class).putExtra("momtype", momtype));
            }
        });
        if (!momtype.equals("2"))
            requestData1();
        else
            requestData2();
        recyclerview = findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        setmadapter();
        if (!momtype.equals("2"))
            recyclerview.setAdapter(adapter1);
        else recyclerview.setAdapter(adapter2);
    }

    //得到班级圈内容
    private void requestData1()
    {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build();
        final User user = new User();
        user.setTeacherId(PrefUtils.getInt(context, "id", 0));
        Gson gson = new Gson();
        String Json = gson.toJson(user);
        final RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8"), Json);
        final Request request = new Request.Builder()
                .url(Urls.PARENTREADCIRCLE)
                .post(requestBody)
                .build();
        Call call = okHttpClient.newCall(request);

        call.enqueue(new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e)
            {
                Log.e("error", "connectFail");

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException
            {
                Message msg = new Message();
                msg.obj = response.body().string();
                msg.what = 1;
                mHandler.sendMessage(msg);
            }
        });
    }

    //得到社区板块内容
    private void requestData2()
    {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build();
        final User user = new User();
        user.setTeacherId(PrefUtils.getInt(context, "teacherId", 0));
        Gson gson = new Gson();
        String Json = gson.toJson(user);
        final RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8"), Json);
        final Request request = new Request.Builder()
                .url(Urls.PARENTREADCOMCIRCLE)
                .post(requestBody)
                .build();
        Call call = okHttpClient.newCall(request);

        call.enqueue(new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e)
            {
                Log.e("error", "connectFail");

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException
            {
                Message msg = new Message();
                msg.obj = response.body().string();
                msg.what = 1;
                mHandler.sendMessage(msg);
                Log.e("社区圈", "asd");
            }
        });
    }

    private void setmadapter()
    {
        if (momtype.equals("2"))
        {
            adapter2 = new BaseRecyclerAdapter()
            {
                @Override
                protected void onBindView(@NonNull BaseViewHolder holder, @NonNull int position)
                {
                    final CommunityCircle communityCircle = communityCircles.get(position);
                    ImageView imageView = holder.getView(R.id.item_mom_img);//图片
                    TextView item_mom_content = holder.getView(R.id.item_mom_content);//内容
                    TextView item_mom_name = holder.getView(R.id.item_mom_name);//姓名
                    TextView item_mom_pinglun = holder.getView(R.id.item_mom_pinglun);//评论
                    CheckBox item_mom_dianzan = holder.getView(R.id.item_mom_dianzan);//点赞
                    if (communityCircle.getZanList() != null)
                    {
                        for (int id : communityCircle.getZanList())
                        {
                            if (id == PrefUtils.getInt(context, "id", 0))
                            {
                                item_mom_dianzan.setChecked(true);
                            } else
                            {
                                item_mom_dianzan.setChecked(false);
                            }
                        }
                    }
                    item_mom_dianzan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
                    {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
                        {
                            if (isChecked)
                            {
                                addFav(communityCircle.getCommunityCircleId());

                            } else
                            {
                                cancelFav(communityCircle.getCommunityCircleId());
                            }
                        }
                    });
                    if (communityCircle.getPicture() != null)
                    {
                        byte[] decodedString = Base64.decode(communityCircle.getPicture(), Base64.DEFAULT);
                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        imageView.setImageBitmap(decodedByte);
                    } else
                    {
                        imageView.setVisibility(View.GONE);
                    }
                    item_mom_content.setText(communityCircle.getContent());
                    item_mom_name.setText(communityCircle.getUserName());
                    item_mom_pinglun.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v)
                        {
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            final EditText editText = new EditText(context);
                            builder.setTitle("提示")
                                    .setView(editText)
                                    .setNegativeButton("取消", null)
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener()
                                    {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which)
                                        {
                                            postComment(editText.getText().toString(), communityCircle.getCommunityCircleId());
                                        }
                                    }).show();
                        }
                    });
                    if (communityCircle.getReplyList() != null)
                    {
                        RecyclerView item_mom_recycle = holder.getView(R.id.item_mom_reply);
                        item_mom_recycle.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                        BaseRecyclerAdapter adapter = new BaseRecyclerAdapter()
                        {
                            @Override
                            protected void onBindView(@NonNull BaseViewHolder holder, @NonNull int position)
                            {
                                Reply reply = communityCircle.getReplyList().get(position);
                                TextView textView = holder.getView(R.id.item_com_content22);
                                textView.setText(reply.getReplyUserName() + "回复" + reply.getUserName() + ":" + reply.getContent());

                            }

                            @Override
                            protected int getLayoutResId(int position)
                            {
                                return R.layout.item_comment;
                            }

                            @Override
                            public int getItemCount()
                            {
                                return communityCircle.getCommentList().size();
                            }
                        };
                        item_mom_recycle.setAdapter(adapter);
                    }
                    if (communityCircle.getCommentList() != null)
                    {
                        RecyclerView item_mom_recycle = holder.getView(R.id.item_mom_recycle);
                        item_mom_recycle.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                        BaseRecyclerAdapter adapter = new BaseRecyclerAdapter()
                        {
                            @Override
                            protected void onBindView(@NonNull BaseViewHolder holder, @NonNull int position)
                            {
                                Comment comment = communityCircle.getCommentList().get(position);
                                TextView textView = holder.getView(R.id.item_com_content22);
                                textView.setText(comment.getUserName() + ":" + comment.getContent());

                            }

                            @Override
                            protected int getLayoutResId(int position)
                            {
                                return R.layout.item_comment;
                            }

                            @Override
                            public int getItemCount()
                            {
                                return communityCircle.getCommentList().size();
                            }
                        };
                        item_mom_recycle.setAdapter(adapter);
                        adapter.setOnItemClickListener(new OnItemClickListener()
                        {
                            @Override
                            public void onItemClick(@NonNull final int position)
                            {
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                final EditText editText = new EditText(context);
                                builder.setTitle("提示")
                                        .setView(editText)
                                        .setNegativeButton("取消", null)
                                        .setPositiveButton("确定", new DialogInterface.OnClickListener()
                                        {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which)
                                            {
                                                postReply(editText.getText().toString(), communityCircle.getCommunityCircleId(), null, communityCircle);
                                            }
                                        }).show();
                            }
                        });
                    }

                }

                @Override
                protected int getLayoutResId(int position)
                {
                    return R.layout.item_moment;
                }

                @Override
                public int getItemCount()
                {
                    return communityCircles.size();
                }
            };
            adapter2.setOnItemClickListener(new OnItemClickListener()
            {
                @Override
                public void onItemClick(@NonNull int position)
                {
                    startActivity(new Intent(context, MomentInfoActivity.class).putExtra("content", communityCircles.get(position).getContent()));
                }
            });
        } else
        {
            adapter1 = new BaseRecyclerAdapter()
            {
                @Override
                protected void onBindView(@NonNull BaseViewHolder holder, @NonNull int position)
                {
                    final ClassCircle classCircle = classCircles.get(position);
                    ImageView imageView = holder.getView(R.id.item_mom_img);//图片
                    TextView item_mom_content = holder.getView(R.id.item_mom_content);//内容
                    TextView item_mom_name = holder.getView(R.id.item_mom_name);//姓名
                    TextView item_mom_pinglun = holder.getView(R.id.item_mom_pinglun);//评论
                    CheckBox item_mom_dianzan = holder.getView(R.id.item_mom_dianzan);//点赞
                    if (classCircle.getZanList() != null)
                    {
                        for (int id : classCircle.getZanList())
                        {
                            if (id == PrefUtils.getInt(context, "id", 0))
                            {
                                item_mom_dianzan.setChecked(true);
                            } else
                            {
                                item_mom_dianzan.setChecked(false);
                            }
                        }
                    }
                    item_mom_dianzan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
                    {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
                        {
                            if (isChecked)
                            {
                                addFav(classCircle.getClassCircleId());
                            } else
                            {
                                cancelFav(classCircle.getClassCircleId());
                            }
                        }
                    });
                    if (classCircle.getPicture() != null)
                    {


                        byte[] decodedString = Base64.decode(classCircle.getPicture(), Base64.DEFAULT);
                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        imageView.setImageBitmap(decodedByte);
                    } else
                    {
                        imageView.setVisibility(View.GONE);
                    }
                    item_mom_content.setText(classCircle.getHomework());
                    //item_mom_name.setText(());
                    item_mom_pinglun.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v)
                        {
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            final EditText editText = new EditText(context);
                            builder.setTitle("提示")
                                    .setView(editText)
                                    .setNegativeButton("取消", null)
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener()
                                    {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which)
                                        {
                                            postComment(editText.getText().toString(), classCircle.getClassCircleId());
                                        }
                                    }).show();
                        }
                    });
                    if (classCircle.getCommentList() != null)
                    {
                        RecyclerView item_mom_recycle = holder.getView(R.id.item_mom_recycle);
                        item_mom_recycle.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                        BaseRecyclerAdapter adapter = new BaseRecyclerAdapter()
                        {
                            @Override
                            protected void onBindView(@NonNull BaseViewHolder holder, @NonNull int position)
                            {
                                Comment comment = classCircle.getCommentList().get(position);
                                TextView textView = holder.getView(R.id.item_com_content22);
                                textView.setText(comment.getUserName() + ":" + comment.getContent());

                            }

                            @Override
                            protected int getLayoutResId(int position)
                            {
                                return R.layout.item_comment;
                            }

                            @Override
                            public int getItemCount()
                            {
                                return classCircle.getCommentList().size();
                            }
                        };
                        item_mom_recycle.setAdapter(adapter);
                        adapter.setOnItemClickListener(new OnItemClickListener()
                        {
                            @Override
                            public void onItemClick(@NonNull int position)
                            {
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                final EditText editText = new EditText(context);
                                builder.setTitle("提示")
                                        .setView(editText)
                                        .setNegativeButton("取消", null)
                                        .setPositiveButton("确定", new DialogInterface.OnClickListener()
                                        {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which)
                                            {
                                                postReply(editText.getText().toString(), classCircle.getClassCircleId(), classCircle, null);
                                            }
                                        }).show();
                            }
                        });
                    }
                }

                @Override
                protected int getLayoutResId(int position)
                {
                    return R.layout.item_moment;
                }

                @Override
                public int getItemCount()
                {
                    return classCircles.size();
                }
            };
            adapter1.setOnItemClickListener(new OnItemClickListener()
            {
                @Override
                public void onItemClick(@NonNull int position)
                {
                    startActivity(new Intent(context, MomentInfoActivity.class).putExtra("content", classCircles.get(position).getHomework()));
                }
            });
        }
    }

    private Handler mHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case 1:
                    String string = (String) msg.obj;
                    Gson gson1 = new Gson();
                    Log.e("ad", string);
                    if (!momtype.equals("2"))
                        classCircles = gson1.fromJson(string, new TypeToken<List<ClassCircle>>()
                        {
                        }.getType());
                    else
                    {
                        communityCircles = gson1.fromJson(string, new TypeToken<List<CommunityCircle>>()
                        {
                        }.getType());
                        Log.e("Asd", string + "");
                    }
                    break;
                case 2:
                    Toast.makeText(context, "添加成功", Toast.LENGTH_SHORT).show();
                    break;
                case 6:
                    break;
                case 7:
                    break;
            }
        }
    };

    @Override
    protected void setupMenu(Menu menu)
    {

    }

    @Override
    protected void onViewClickListener(View view)
    {
        switch (view.getId())
        {
            case R.id.add_moments:
                startActivity(new Intent(context, AddMomentsActivity.class));
                break;
        }
    }

    @Override
    protected void onBackClickOpt()
    {
        finish();
    }
}
