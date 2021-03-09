import {
  GestureResponderEvent,
  requireNativeComponent,
  StyleProp,
  ViewStyle,
} from 'react-native';
import type {
  Orientation,
  PageScrollStateChangedNativeEvent,
  TransitionStyle,
  ViewPagerOnPageScrollEvent,
  ViewPagerOnPageSelectedEvent,
} from './types';

const VIEW_MANAGER_NAME = 'RNCViewPager';

type ViewPagerNativeProps = {
  count: number;
  offscreenPageLimit?: number;
  offset: number;
  onMoveShouldSetResponderCapture: (event: GestureResponderEvent) => boolean;
  onPageScroll?: (event: ViewPagerOnPageScrollEvent) => void;
  onPageScrollStateChanged?: (event: PageScrollStateChangedNativeEvent) => void;
  onPageSelected?: (event: ViewPagerOnPageSelectedEvent) => void;
  orientation?: Orientation;
  overdrag?: boolean;
  pageMargin?: number;
  scrollEnabled?: boolean;
  showPageIndicator?: boolean;
  style: StyleProp<ViewStyle>;
  transitionStyle?: TransitionStyle;
};

export const ViewPagerNative = requireNativeComponent<ViewPagerNativeProps>(
  VIEW_MANAGER_NAME
);
