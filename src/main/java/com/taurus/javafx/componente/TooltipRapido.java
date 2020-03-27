package com.taurus.javafx.componente;

import com.taurus.javafx.util.JavafxUtil;
import javafx.scene.control.Tooltip;

/**
 *
 * @author Diego
 */
public class TooltipRapido extends Tooltip {

    public TooltipRapido() {
        super();
        JavafxUtil.acelerarExibicaoTooltip(this);
    }

    public TooltipRapido(String text) {
        super(text);
        JavafxUtil.acelerarExibicaoTooltip(this);
    }

}
