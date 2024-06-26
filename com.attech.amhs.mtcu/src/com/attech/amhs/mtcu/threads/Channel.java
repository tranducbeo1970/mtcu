/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.amhs.mtcu.threads;

import org.slf4j.Logger;

/**
 *
 * @author andh
 */
public abstract class Channel extends Thread {
    
    private boolean isRunning;
    private boolean isRequestStop = false;

    public void printStatus() {

        final State state = super.getState();
        long id = this.getId();
        switch (state) {
            case BLOCKED:
                getLog().error("Dead ({})", state);
                break;
            case NEW:
                getLog().warn("Status is not running ({})", state);
                // logger.info(String.format("%s (Thread ID: %s) IS NOT RUNNING (%s)", thread.toString(), thread.getId(), state));
                break;
            case RUNNABLE:
                getLog().info("Still alive ({})", state);
                // logger.info(String.format("%s (Thread ID: %s) IS ALIVE (%s)", thread.toString(), thread.getId(), state));
                break;
            case TERMINATED:
                getLog().error("Dead ({})", state);
                // logger.info(String.format("%s (Thread ID: %s) IS DEAD (%s)", thread.toString(), thread.getId(), state));
                break;
            case TIMED_WAITING:
                getLog().info("Still alive ({})", state);
                // logger.info(String.format("%s (Thread ID: %s) IS ALIVE (%s)", thread.toString(), thread.getId(), state));
                break;
            case WAITING:
                getLog().info("Still alive ({})", state);
                // logger.info(String.format("%s (Thread ID: %s) IS ALIVE (%s)", thread.toString(), thread.getId(), state));
                break;
            default:
                getLog().warn("Status is unknown ({})", state);
                // logger.info(String.format("%s (Thread ID: %s) IS UNKNOWN (%s)", thread.toString(), thread.getId(), state));
                break;
        }
    }

    protected synchronized void requestStop() {
        this.isRequestStop = true;
    }

    protected synchronized void setRunning(boolean value) {
        this.isRunning = value;
    }

    protected boolean isRequestStoped() {
        return this.isRequestStop;
    }

    public synchronized boolean isRunning() {
        return this.isRunning;
    }

    protected abstract Logger getLog();

}
