package dev.vnco.hub.command.api.internal;

import java.util.Map;

import com.google.common.base.Preconditions;

import lombok.AllArgsConstructor;
import dev.vnco.hub.command.api.Key;
import dev.vnco.hub.command.api.Provider;
import dev.vnco.hub.command.api.binding.Binding;
import dev.vnco.hub.command.api.binding.BindingBuilder;
import dev.vnco.hub.command.api.defaults.instance.InstanceProvider;

@AllArgsConstructor
public class AbstractBindingBuilder<T> implements BindingBuilder<T> {

	public Map<Key<?>, Binding<?>> map;
	public InternalBinding<T> binding;

    @Override
    public void toProvider(Provider<T> provider) {
        Preconditions.checkState(binding.getProvider() == null, "Provider already bound.");
        
        binding.setProvider(provider);
        
        map.put(binding.getKey(), binding);
    }

    @Override
    public void toInstance(T instance) {
        Preconditions.checkState(binding.getProvider() == null, "Provider already bound.");
        
        binding.setProvider(new InstanceProvider<T>(instance));
        
        map.put(binding.getKey(), binding);
    }
}
