<?php

namespace App;

use Laravel\Passport\HasApiTokens;
use Illuminate\Notifications\Notifiable;
use Illuminate\Foundation\Auth\User as Authenticatable;

class User extends Authenticatable
{
    use HasApiTokens, Notifiable;

    /**
     * The attributes that are mass assignable.
     *
     * @var array
     */
    protected $fillable = [
        'name', 
        'email', 
        'password', 
        'gender', 
        'bornPlace', 
        'bornDate', 
        'phone',
        'province',
        'city',
        'address', 
        'location',
        'religion',
        'race',
        'userType',
        'status',
    ];

    /**
     * The attributes that should be hidden for arrays.
     *
     * @var array
     */
    protected $hidden = [
        'password', 'remember_token',
    ];

    /**
     * Get the province record associated with the user.
     */
    public function province()
    {
        return $this->belongsTo('App\Places', 'province');
    }

    /**
     * Get the city record associated with the user.
     */
    public function city()
    {
        return $this->belongsTo('App\Places', 'city');
    }

    /**
     * Get the userWallet record associated with the user.
     */
    public function userWallet()
    {
        return $this->hasMany('App\UserWallet');
    }

    /**
     * Get the userLanguage record associated with the user.
     */
    public function userLanguage()
    {
        return $this->hasMany('App\UserLanguage');
    }

    /**
     * Get the userJob record associated with the user.
     */
    public function userJob()
    {
        return $this->hasMany('App\UserJob');
    }

    /**
     * Get the userWorkTime record associated with the user.
     */
    public function userWorkTime()
    {
        return $this->hasMany('App\UserWorkTime');
    }

    /**
     * Get the userDocument record associated with the user.
     */
    public function userDocument()
    {
        return $this->hasMany('App\UserDocument');
    }

    /**
     * Get the userAdditionalInfo record associated with the user.
     */
    public function userAdditionalInfo()
    {
        return $this->hasMany('App\UserAdditionalInfo');
    }

    /**
     * Get the walletTransaction record associated with the user.
     */
    public function walletTransaction()
    {
        return $this->hasMany('App\WalletTransaction');
    }
}