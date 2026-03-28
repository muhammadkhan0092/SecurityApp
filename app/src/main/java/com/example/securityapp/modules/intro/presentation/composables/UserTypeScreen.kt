package com.example.securityapp.modules.intro.presentation.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.example.securityapp.app.Route
import com.example.securityapp.app.sharedHiltViewModel
import com.example.securityapp.modules.intro.presentation.models.IntroAction
import com.example.securityapp.modules.intro.presentation.models.IntroState
import com.example.securityapp.modules.intro.presentation.models.UserType
import com.example.securityapp.modules.intro.presentation.vm.IntroSharedVm
import com.example.securityapp.modules.packages.components.Radio
import com.example.securityapp.ui.theme.Purple40


@Composable
fun UserTypeScreenRoot(navController: NavHostController, entry: NavBackStackEntry) {
    val sharedVm = entry.sharedHiltViewModel<IntroSharedVm>(navController)
    val state=  sharedVm.state.collectAsStateWithLifecycle()
    UserTypeScreen(state.value) {
        when(it){
            IntroAction.OnContinueClicked -> navController.navigate(Route.Login)
            else -> sharedVm.onAction(it)
        }
    }
}
@Composable
fun UserTypeScreen(
    state: IntroState,
    onAction : (IntroAction)-> Unit
) {
    Box(modifier = Modifier.fillMaxSize().safeContentPadding()){
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                "User Type",
                fontWeight = FontWeight.ExtraBold,
                fontSize = 30.sp,
                color = Purple40,
                fontFamily = FontFamily.Serif
            )
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                "I Am",
                fontSize = 22.sp,
                fontWeight = FontWeight.ExtraBold,
                fontFamily = FontFamily.SansSerif
            )
            Spacer(modifier = Modifier.height(10.dp))
            UserTypeComponent(
                isSelected = state.userType== UserType.CONTROLLED,
                title = "Controlled",
                content = "Other Devices Linked to you can control your device",
                onClick = {
                    onAction(IntroAction.OnBeControlled)
                }
            )
            Spacer(modifier = Modifier.height(10.dp))
            UserTypeComponent(
                isSelected = state.userType== UserType.CONTROLLER,
                title = "Controller",
                content = "Device Linked to you can be controlled",
                onClick = {
                    onAction(IntroAction.OnControl)
                }
            )
            Spacer(modifier = Modifier.fillMaxWidth().weight(1f))
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Purple40.copy(alpha = 0.6f),
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(5.dp),
                onClick ={onAction(IntroAction.OnContinueClicked)}
            ) {
                Text("Continue",fontFamily = FontFamily.Cursive)
            }
//            Button(
//                modifier = Modifier.fillMaxWidth().background(Purple40, RoundedCornerShape(10.dp)),
//                onClick ={onAction(IntroAction.OnContinueClicked)}) {
//                Text("Continue",fontFamily = FontFamily.Cursive)
//            }
        }
    }
}

@Composable
fun UserTypeComponent(
    isSelected: Boolean,
    title: String,
    content: String,
    onClick: () -> Unit
){
    Row(
        modifier = Modifier.fillMaxWidth().clickable{
            onClick()
        }
    ){
        Radio(isSelected)
        Spacer(modifier = Modifier.width(10.dp))
        Column(
            modifier = Modifier.weight(1f)
        ){
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
                fontFamily = FontFamily.Cursive
            )
            Text(
                text = content,
                fontWeight = FontWeight.Medium,
                fontSize = 22.sp,
                fontFamily = FontFamily.Cursive
            )
        }
    }
}