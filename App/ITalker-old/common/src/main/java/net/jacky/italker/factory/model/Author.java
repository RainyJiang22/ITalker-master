package net.jacky.italker.factory.model;

/**
 * 基础用户接口
 *
 * @author jacky
 * @version 1.0.0
 */
public interface Author {
    String getId();

    void setId(String id);

    String getName();

    void setName(String name);

    String getPortrait();

    void setPortrait(String portrait);
}
