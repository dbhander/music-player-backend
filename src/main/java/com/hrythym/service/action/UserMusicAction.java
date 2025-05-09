package com.hrythym.service.action;

public abstract class UserMusicAction {

    /** Template method: fixed flow */
    public final void execute(ActionContext ctx) {
        try {
            onBeforeAction(ctx);
            logAction(ctx);
            performCoreAction(ctx);
            onAfterAction(ctx);
        } catch(Exception ex) {
            onFailure(ctx, ex);
            throw ex;
        }
    }

    /** Hook: e.g. notify observers */
    protected void onBeforeAction(ActionContext ctx) {}

    /** Step 1: always log */
    protected abstract void logAction(ActionContext ctx);

    /** Step 2: do the real work (play/pause/create playlist) */
    protected abstract void performCoreAction(ActionContext ctx);

    /** Hook: e.g. send events, trigger achievements */
    protected void onAfterAction(ActionContext ctx) {}

    /** Optional hook for error handling */
    protected void onFailure(ActionContext ctx, Exception ex) {
        // default: swallow or log
    }
}
