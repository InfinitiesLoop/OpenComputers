package li.cil.oc.server.component

import java.util

import li.cil.oc.Constants
import li.cil.oc.api.driver.DeviceInfo.DeviceAttribute
import li.cil.oc.api.driver.DeviceInfo.DeviceClass
import li.cil.oc.Settings
import li.cil.oc.api.Network
import li.cil.oc.api.driver.DeviceInfo
import li.cil.oc.api.machine.Arguments
import li.cil.oc.api.machine.Callback
import li.cil.oc.api.machine.Context
import li.cil.oc.api.network.Visibility
import li.cil.oc.api.prefab
import li.cil.oc.api.prefab.network.{AbstractManagedNodeContainer, AbstractManagedNodeContainer}
import li.cil.oc.common.item.TabletWrapper

import scala.collection.convert.WrapAsJava._

class Tablet(val tablet: TabletWrapper) extends AbstractManagedNodeContainer with DeviceInfo {
  override val getNode = Network.newNode(this, Visibility.NETWORK).
    withComponent("tablet").
    withConnector(Settings.Power.Buffer.tablet).
    create()

  private final lazy val deviceInfo = Map(
    DeviceAttribute.Class -> DeviceClass.System,
    DeviceAttribute.Description -> "Tablet",
    DeviceAttribute.Vendor -> Constants.DeviceInfo.DefaultVendor,
    DeviceAttribute.Product -> "Jogger",
    DeviceAttribute.Capacity -> tablet.getSizeInventory.toString
  )

  override def getDeviceInfo: util.Map[String, String] = deviceInfo

  // ----------------------------------------------------------------------- //

  @Callback(doc = """function():number -- Gets the pitch of the player holding the tablet.""")
  def getPitch(context: Context, args: Arguments): Array[AnyRef] = result(tablet.player.rotationPitch)
}
