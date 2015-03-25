/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fr.kouignamann.battlestar.core.controlers;

import org.lwjgl.input.Mouse;

import fr.kouignamann.battlestar.core.graphics.GraphicContext;
import static fr.kouignamann.battlestar.core.commons.GameConstant.*;

import java.util.Objects;

/**
 *
 * @author Guillaume BERTRAND
 */
public class MouseListener implements Listener {
	
    private float xMovement;
    private float yMovement;
	
    private boolean leftClick;

    private Integer xPressed;
    private Integer yPressed;

    private Integer xReleased;
    private Integer yReleased;
    
    public MouseListener() {
        super();
        Mouse.setClipMouseCoordinatesToWindow(false);
        xMovement = 0;
        yMovement = 0;
    }
    
    @Override
    public void listen() {
        listenLeftClick();
        listenWheel();
    }

    private void listenWheel() {
        float dWheel;
        if ((dWheel = (float)Mouse.getDWheel()) != 0) {
            GraphicContext.addCameraMovement((dWheel*WHEEL_SENSITIVITY)/10000.0f);
        }
    }
    
    private void listenLeftClick() {
        if (Mouse.isButtonDown(0)) {
            if (leftClick) {
                if (!this.isInsideWindow()) {
                    leftClick = false;
                } else {
                    xReleased = Mouse.getX();
                    yReleased = Mouse.getY();
                    leftAction();
                }
            }
            else {
                leftClick = true;
                xPressed = Mouse.getX();
                yPressed = Mouse.getY();
            }
        } else {
            if (leftClick) {
                if (this.isInsideWindow()) {
                    xReleased = Mouse.getX();
                    yReleased = Mouse.getY();
                    leftAction();
                } else {
                    leftClick = false;
                }
            }
            leftClick = false;
        }
    }

    private void leftAction() {
        if (Objects.equals(xPressed,xReleased)
                && Objects.equals(yPressed, yReleased)) {
            this.leftSelectionClick();
        } else {
            this.leftRotationClick();
        }
    }

    private boolean isInsideWindow() {
        return (Mouse.getX()>=0
                && Mouse.getX() <= SCREEN_WIDTH
                && Mouse.getY()>=0
                && Mouse.getY() <= SCREEN_HEIGHT);
    }

    private void leftSelectionClick() {
    }

    private void leftRotationClick() {
        xMovement = (xReleased - xPressed)*MOUSE_SENSITIVITY/500000.f;
        yMovement = (yReleased - yPressed)*MOUSE_SENSITIVITY/500000.f;

        GraphicContext.addCameraRotation(xMovement, yMovement);
    }
}
