package com.capstone.momomeal.feature
import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import androidx.annotation.IdRes
import androidx.core.content.res.use
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import androidx.navigation.NavDestination
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import com.capstone.momomeal.R

import java.util.*

@Navigator.Name("bottom")
class BottomNavigator(
    @IdRes private val fragmentContainerId: Int,
    private val fragmentManager: FragmentManager
) : Navigator<BottomNavigator.Destination>() {
    private val backStack: Deque<String> = ArrayDeque()

    override fun createDestination(): Destination = Destination(this)

    override fun navigate(
        destination: Destination,
        args: Bundle?,
        navOptions: NavOptions?,
        navigatorExtras: Extras?
    ): NavDestination? {
        val className = destination.className ?: return null
        val tag = className.split('.').last()

        if (backStack.peekLast() == tag) {
            return null
        }

        if (backStack.peekLast() != tag) {
            backStack.addLast(tag)
        }

        val current = fragmentManager.findFragmentByTag(tag)
        fragmentManager.commit {
            if (current == null) {
                val fragment = fragmentManager.fragmentFactory.instantiate(
                    ClassLoader.getSystemClassLoader(),
                    className
                )
                add(fragmentContainerId, fragment, tag)
            } else {
                show(current)
            }

            hideOthers(tag)
        }

        return destination
    }

    override fun popBackStack(): Boolean {
        val tag = backStack.pollLast() ?: return true
        val newCurrentTag = backStack.peekLast() ?: return true
        val newCurrent = fragmentManager.findFragmentByTag(newCurrentTag)
        fragmentManager.commit {
            newCurrent?.let {
                show(it)
                hideOthers(newCurrentTag)
            }
        }
        return true
    }

    private fun FragmentTransaction.hideOthers(tag: String) {
        val others = MainTab.otherTab(exceptTag = tag)
            .mapNotNull {
                fragmentManager.findFragmentByTag(it.tag)
            }
        others.forEach {
            hide(it)
        }
    }

    override fun onSaveState(): Bundle {
        return bundleOf(KEY_BACK_STACK to backStack.toTypedArray())
    }

    override fun onRestoreState(savedState: Bundle) {
        val savedBackStack = savedState.getStringArray(KEY_BACK_STACK)
        if (savedBackStack != null) {
            backStack.clear()
            backStack.addAll(savedBackStack)
        }
    }

    @NavDestination.ClassType(Fragment::class)
    class Destination(navigator: BottomNavigator) : NavDestination(navigator) {
        internal var className: String? = null
            private set

        override fun onInflate(context: Context, attrs: AttributeSet) {
            super.onInflate(context, attrs)

            className = context.resources.obtainAttributes(attrs, R.styleable.BottomNavigator)
                .use {
                    it.getString(R.styleable.BottomNavigator_android_name)
                }
        }
    }

    companion object {
        private const val KEY_BACK_STACK = "BottomNavigator.BackStack"
    }
}

enum class MainTab(
    val itemId: Int,
    val tag: String
) {
    HOME(R.id.itemHome, "HomeFragment"),
    ChatRoom(R.id.itemChatRoom, "ChatroomFragment"),
    MyPage(R.id.itemMypage, "MypageFragment");

    companion object {
        fun from(itemId: Int): MainTab? = values().firstOrNull { it.itemId == itemId }
    }
}

fun MainTab.Companion.otherTab(exceptTag: String): Sequence<MainTab> =
    MainTab.values()
        .asSequence()
        .filter {
            it.tag != exceptTag
        }