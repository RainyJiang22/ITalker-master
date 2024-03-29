package net.jacky.italker.factory.model.db;

import com.raizlabs.android.dbflow.structure.BaseModel;

import net.jacky.italker.factory.utils.DiffUiDataCallback;

/**
 * 我们APP中的基础的一个BaseDbModel，
 * 基础了数据库框架DbFlow中的基础类
 * 同时定义类我们需要的方法
 *
 * @author jacky
 * @version 1.0.0
 */
public abstract class BaseDbModel<Model> extends BaseModel
        implements DiffUiDataCallback.UiDataDiffer<Model> {
}
