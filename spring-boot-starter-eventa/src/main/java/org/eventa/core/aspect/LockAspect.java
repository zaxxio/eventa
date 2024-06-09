package org.eventa.core.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.After;

import org.springframework.stereotype.Component;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Aspect
@Component
public class LockAspect {

}
