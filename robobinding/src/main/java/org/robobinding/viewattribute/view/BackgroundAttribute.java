/**
 * Copyright 2011 Cheng Wei, Robert Taylor
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 */
package org.robobinding.viewattribute.view;

import org.robobinding.binder.PropertyViewAttribute;
import org.robobinding.presentationmodel.PresentationModelAdapter;
import org.robobinding.viewattribute.AbstractReadOnlyPropertyViewAttribute;
import org.robobinding.viewattribute.PrimitiveTypeUtils;
import org.robobinding.viewattribute.PropertyBindingDetails;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class BackgroundAttribute implements PropertyViewAttribute
{
	private final View view;
	private final PropertyBindingDetails propertyBindingDetails;

	public BackgroundAttribute(View view, String attributeValue, boolean preInitializeView)
	{
		this.view = view;
		propertyBindingDetails = PropertyBindingDetails.createFrom(attributeValue, preInitializeView);
	}

	@Override
	public void bind(PresentationModelAdapter presentationModelAdapter, Context context)
	{
		PropertyViewAttribute propertyViewAttribute = lookupPropertyViewAttribute(presentationModelAdapter);
		propertyViewAttribute.bind(presentationModelAdapter, context);
	}

	PropertyViewAttribute lookupPropertyViewAttribute(PresentationModelAdapter presentationModelAdapter)
	{
		Class<?> propertyType = presentationModelAdapter.getPropertyType(propertyBindingDetails.propertyName);

		if (PrimitiveTypeUtils.integerIsAssignableFrom(propertyType))
		{
			return new ResourceBackgroundAttribute();
		} else if (Drawable.class.isAssignableFrom(propertyType))
		{
			return new DrawableBackgroundAttribute();
		}

		throw new RuntimeException("Could not find a suitable background attribute class for property type: " + propertyType);
	}

	private abstract class AbstractBackgroundAttribute<T> extends AbstractReadOnlyPropertyViewAttribute<T>
	{
		public AbstractBackgroundAttribute()
		{
			super(propertyBindingDetails);
		}
	}

	private class ResourceBackgroundAttribute extends AbstractBackgroundAttribute<Integer>
	{
		@Override
		protected void valueModelUpdated(Integer newResourceId)
		{
			if(newResourceId != null)
			{
				view.setBackgroundResource(newResourceId);
			}
		}
	}

	private class DrawableBackgroundAttribute extends AbstractBackgroundAttribute<Drawable>
	{
		@Override
		protected void valueModelUpdated(Drawable newDrawable)
		{
			view.setBackgroundDrawable(newDrawable);
		}
	}
}