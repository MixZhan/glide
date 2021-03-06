package com.bumptech.glide.test;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.test.InstrumentationRegistry;
import android.support.v4.content.res.ResourcesCompat;
import com.google.common.truth.FailureStrategy;
import com.google.common.truth.Subject;
import com.google.common.truth.SubjectFactory;
import com.google.common.truth.Truth;

/**
 * Truth assertions for comparing {@link Bitmap}s.
 */
final class BitmapSubject extends Subject<BitmapSubject, Bitmap> {

  private static final SubjectFactory<BitmapSubject, Bitmap> FACTORY =
      new SubjectFactory<BitmapSubject, Bitmap>() {
        @Override
        public BitmapSubject getSubject(FailureStrategy fs, Bitmap that) {
          return new BitmapSubject(fs, that);
        }
      };

  private BitmapSubject(FailureStrategy failureStrategy,
      Bitmap subject) {
    super(failureStrategy, subject);
  }

  static BitmapSubject assertThat(Drawable drawable) {
    if (!(drawable instanceof BitmapDrawable)) {
      throw new IllegalArgumentException("Not a BitmapDrawable: " + drawable);
    }
    return assertThat(((BitmapDrawable) drawable).getBitmap());
  }

  static BitmapSubject assertThat(Bitmap bitmap) {
    return Truth.assertAbout(FACTORY).that(bitmap);
  }

  @Override
  protected String getDisplaySubject() {
    return getDisplayString(getSubject());
  }

  private static String getDisplayString(Bitmap bitmap) {
     return "<"
        + "[" + bitmap.getWidth() + "x" + bitmap.getHeight() + "]"
        + " "
        + bitmap.getConfig()
        + ">";
  }

  void sameAs(@DrawableRes int resourceId) {
    Context context = InstrumentationRegistry.getTargetContext();
    Drawable drawable =
        ResourcesCompat.getDrawable(context.getResources(), resourceId, context.getTheme());
    sameAs(drawable);
  }

  void isMutable()  {
    if (!getSubject().isMutable()) {
      fail("is mutable");
    }
  }

  void isImmutable() {
    if (getSubject().isMutable()) {
      fail("is immutable");
    }
  }

  @SuppressWarnings("unchecked")
  void sameAs(Drawable other) {
    if (!(other instanceof BitmapDrawable)) {
      fail("Not a BitmapDrawable");
    }
    sameAs(((BitmapDrawable) other).getBitmap());
  }

  void sameAs(Bitmap other) {
    if (!getSubject().sameAs(other)) {
      fail("is the same as " + getDisplayString(other));
    }
  }
}
