package com.example

import androidx.compose.material3.Surface
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onRoot
import com.example.data.model.City
import com.example.data.model.UserAccount
import com.example.data.model.UserRole
import com.example.ui.components.OwnShopTopBar
import com.example.ui.theme.OwnShopTheme
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import com.github.takahirom.roborazzi.captureRoboImage
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(qualifiers = RobolectricDeviceQualifiers.Pixel8, sdk = [36])
class GreetingScreenshotTest {

  @get:Rule val composeTestRule = createComposeRule()

  @Test
  fun greeting_screenshot() {
    composeTestRule.setContent {
      OwnShopTheme {
        Surface {
          OwnShopTopBar(
            selectedCity = City("jaipur", "Jaipur", "Rajasthan", true, listOf("Vaishali Nagar")),
            currentUser = UserAccount(
              id = "u1",
              name = "Vikas Singhal",
              email = "customer@ownshop.com",
              phone = "+91 97845 12000",
              role = UserRole.CUSTOMER,
              cityId = "jaipur",
              cityName = "Jaipur",
              address = "Vaishali Nagar, Jaipur"
            ),
            onCityClick = {},
            onRoleSwitchClick = {}
          )
        }
      }
    }

    composeTestRule.onRoot().captureRoboImage(filePath = "src/test/screenshots/greeting.png")
  }
}

