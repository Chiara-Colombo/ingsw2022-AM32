package it.polimi.ingsw.server;

import it.polimi.ingsw.model.EffectHandler;

@FunctionalInterface
interface IInstantiateEffect {
    EffectHandler instantiateEffect();
}