## Migration Guide

# 5.0.x -> 5.1.x
Before:
```js
import ViewPager from '@react-native-community/viewpager'
import type { ViewPagerOnPageScrollEventData,ViewPagerOnPageSelectedEventData } from '@react-native-community/viewpager';
```

After:
```js
import PagerView from '@nathansbird/react-native-pager-view';
import type { PagerViewOnPageScrollEventData, PagerViewOnPageSelectedEventData } from '@nathansbird/react-native-pager-view';
