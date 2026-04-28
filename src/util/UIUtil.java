package util;

import java.awt.GridBagConstraints;
import java.awt.Insets;

/**
 * UI工具类：封装通用UI组件配置
 */
public class UIUtil {
    /**
     * 创建通用GridBagConstraints
     */
    public static GridBagConstraints createGBC() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // 边距
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        return gbc;
    }
}