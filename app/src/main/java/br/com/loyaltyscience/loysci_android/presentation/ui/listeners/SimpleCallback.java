package br.com.loyaltyscience.loysci_android.presentation.ui.listeners;

public interface SimpleCallback<T> {
    void onResponse(T response);
    void onError(Throwable t);
}
