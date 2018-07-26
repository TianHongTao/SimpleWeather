package com.simpleweather.simpleweather.Calendar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

public class CalendarCard extends View {

    private static final int TOTAL_COL = 7;
    private static final int TOTAL_ROW = 6;
    public static final String CURRENT_DAY_COLOR_STRING = "#3F51B5";

    private Paint circlePaint; // 绘制圆形的画笔
    private Paint textPaint; // 绘制文本的画笔
    private int cellSpace; // 单元格间距
    private Row rows[] = new Row[TOTAL_ROW]; // 行数组，每个元素代表一行
    private static CustomDate showDate; // 自定义的日期，包括year,month,day
    private OnCellClickListener cellClickListener; // 单元格点击回调事件
    private int touchSlop; //
    private boolean callBackCellSpace;

    private Cell clickCell;
    private float downX;
    private float downY;

    public interface OnCellClickListener {
        void clickDate(CustomDate date); // 回调点击的日期

        void changeDate(CustomDate date); // 回调滑动ViewPager改变的日期
    }

    public CalendarCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public CalendarCard(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CalendarCard(Context context) {
        super(context);
        init(context);
    }

    public CalendarCard(Context context, OnCellClickListener listener) {
        super(context);
        this.cellClickListener = listener;
        init(context);
    }

    private void init(Context context) {
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circlePaint.setStyle(Paint.Style.FILL);
        circlePaint.setColor(Color.parseColor(CURRENT_DAY_COLOR_STRING)); // 红色圆形
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();

        initDate();
    }

    private void initDate() {
        showDate = new CustomDate();
        fillDate();//
    }

    private void fillDate() {
        int monthDay = DateUtil.getCurrentMonthDay(); // 今天
        int lastMonthDays = DateUtil.getMonthDays(showDate.year,
                showDate.month - 1); // 上个月的天数
        int currentMonthDays = DateUtil.getMonthDays(showDate.year,
                showDate.month); // 当前月的天数
        int firstDayWeek = DateUtil.getWeekDayFromDate(showDate.year,
                showDate.month);
        boolean isCurrentMonth = false;
        if (DateUtil.isCurrentMonth(showDate)) {
            isCurrentMonth = true;
        }
        int day = 0;
        for (int j = 0; j < TOTAL_ROW; j++) {
            rows[j] = new Row(j);
            for (int i = 0; i < TOTAL_COL; i++) {
                int position = i + j * TOTAL_COL; // 单元格位置
                // 这个月的
                if (position >= firstDayWeek
                        && position < firstDayWeek + currentMonthDays) {
                    day++;
                    rows[j].cells[i] = new Cell(CustomDate.modifiDayForObject(
                            showDate, day), State.CURRENT_MONTH_DAY, i, j);
                    // 今天
                    if (isCurrentMonth && day == monthDay) {
                        CustomDate date = CustomDate.modifiDayForObject(showDate, day);
                        rows[j].cells[i] = new Cell(date, State.TODAY, i, j);
                    }

                    if (isCurrentMonth && day > monthDay) { // 如果比这个月的今天要大，表示还没到
                        rows[j].cells[i] = new Cell(
                                CustomDate.modifiDayForObject(showDate, day),
                                State.UNREACH_DAY, i, j);
                    }

                    // 过去一个月
                } else if (position < firstDayWeek) {
                    rows[j].cells[i] = new Cell(new CustomDate(showDate.year,
                            showDate.month - 1, lastMonthDays
                            - (firstDayWeek - position - 1)),
                            State.PAST_MONTH_DAY, i, j);
                    // 下个月
                } else if (position >= firstDayWeek + currentMonthDays) {
                    rows[j].cells[i] = new Cell((new CustomDate(showDate.year,
                            showDate.month + 1, position - firstDayWeek
                            - currentMonthDays + 1)),
                            State.NEXT_MONTH_DAY, i, j);
                }
            }
        }
        cellClickListener.changeDate(showDate);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < TOTAL_ROW; i++) {
            if (rows[i] != null) {
                rows[i].drawCells(canvas);
            }
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        // 视图的宽度
        int viewWidth = w;
        // 视图的高度
        int viewHeight = h;
        cellSpace = Math.min(viewHeight / TOTAL_ROW, viewWidth / TOTAL_COL);
        if (!callBackCellSpace) {
            callBackCellSpace = true;
        }
        textPaint.setTextSize(cellSpace / 3);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                downY = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                float disX = event.getX() - downX;
                float disY = event.getY() - downY;
                if (Math.abs(disX) < touchSlop && Math.abs(disY) < touchSlop) {
                    int col = (int) (downX / cellSpace);
                    int row = (int) (downY / cellSpace);
                    measureClickCell(col, row);
                }
                break;
            default:
                break;
        }

        return true;
    }


    private void measureClickCell(int col, int row) {
        if (col >= TOTAL_COL || row >= TOTAL_ROW)
            return;
        if (clickCell != null) {
            rows[clickCell.j].cells[clickCell.i] = clickCell;
        }
        if (rows[row] != null) {
            clickCell = new Cell(rows[row].cells[col].date,
                    rows[row].cells[col].state, rows[row].cells[col].i,
                    rows[row].cells[col].j);

            CustomDate date = rows[row].cells[col].date;
            date.week = col;
            cellClickListener.clickDate(date);

            // 刷新界面
            update();
        }
    }


    class Row {
        int j;

        Row(int j) {
            this.j = j;
        }

        Cell[] cells = new Cell[TOTAL_COL];

        // 绘制单元格
        void drawCells(Canvas canvas) {
            for (Cell cell : cells) {
                if (cell != null) {
                    cell.drawSelf(canvas);
                }
            }
        }

    }

    class Cell {
        public CustomDate date;
        State state;
        int i;
        int j;

        Cell(CustomDate date, State state, int i, int j) {
            super();
            this.date = date;
            this.state = state;
            this.i = i;
            this.j = j;
        }

        void drawSelf(Canvas canvas) {
            switch (state) {
                case TODAY: // 今天
                    textPaint.setColor(Color.parseColor("#fffffe"));
                    canvas.drawCircle((float) (cellSpace * (i + 0.5)),
                            (float) ((j + 0.5) * cellSpace), cellSpace / 3,
                            circlePaint);
                    break;
                case CURRENT_MONTH_DAY: // 当前月日期
                    textPaint.setColor(Color.BLACK);
                    break;
                case PAST_MONTH_DAY: // 过去一个月
                case NEXT_MONTH_DAY: // 下一个月
                    textPaint.setColor(Color.parseColor("#fffffe"));
                    break;
                case UNREACH_DAY: // 还未到的天
                    textPaint.setColor(Color.GRAY);
                    break;
                default:
                    break;
            }
            // 绘制文字
            String content = date.day + "";
            canvas.drawText(content,
                    (float) ((i + 0.5) * cellSpace - textPaint
                            .measureText(content) / 2), (float) ((j + 0.7)
                            * cellSpace - textPaint
                            .measureText(content, 0, 1) / 2), textPaint);
        }
    }


    enum State {
        TODAY, CURRENT_MONTH_DAY, PAST_MONTH_DAY, NEXT_MONTH_DAY, UNREACH_DAY;
    }

    // 从左往右划，上一个月
    public void leftSlide() {
        if (showDate.month == 1) {
            showDate.month = 12;
            showDate.year -= 1;
        } else {
            showDate.month -= 1;
        }
        update();
    }

    // 从右往左划，下一个月
    public void rightSlide() {
        if (showDate.month == 12) {
            showDate.month = 1;
            showDate.year += 1;
        } else {
            showDate.month += 1;
        }
        update();
    }

    public void update() {
        fillDate();
        invalidate();
    }

}
